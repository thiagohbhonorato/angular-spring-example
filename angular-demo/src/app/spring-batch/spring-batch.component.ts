import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {
  catchError,
  observable,
  Observable,
  of,
  take,
  tap,
  throwError,
} from 'rxjs';
import { SpringBatchService } from './spring-batch.service';

interface Job {
  id: string;
  name: string;
  status: 'No executed' | 'Running' | 'Completed' | 'Failed';
  description: string;
  service: string;
  running: boolean;
}

interface Response {
  status: 'No executed' | 'Running' | 'Completed' | 'Failed';
  start_time: string;
  end_time: string;
  elapsed_time: string;
}

interface Log {
  job: Job;
  res: Response;
  err: HttpErrorResponse;
}

interface File {
  name: string;
  creation_date: string;
}

@Component({
  selector: 'app-spring-batch',
  templateUrl: './spring-batch.component.html',
  styleUrls: ['./spring-batch.component.css'],
})
export class SpringBatchComponent {
  jobs: Job[];
  logs: Log[];

  files: File[];
  filesColumns = ['name', 'creation_date'];

  constructor(private service: SpringBatchService) {
    this.logs = [];
    this.jobs = [
      <Job>{
        id: 'databaseToFile',
        name: 'Database To File',
        status: 'No executed',
        description: 'Database loading for file creation.',
        service: '/job/database-to-file',
      },
      <Job>{
        id: 'fileToDatabase',
        name: 'File To Database',
        status: 'No executed',
        description: 'File loading for databse insertion.',
        service: '/job/file-to-database',
      },
    ];

    this.files = [];
    this.loadTableFiles();
  }

  runJob(job: Job): void {
    job.status = 'Running';
    job.running = true;

    this.service
      .post<Response>(job.service)
      .pipe(
        catchError((error) => {
          this.onJobError(job, error);
          return throwError(() => new Error(error));
        })
      )
      .subscribe({
        next: (res) => {
          this.onJobSuccessfully(job, <Response>res);
        },
        error() {
          job.status = 'Failed';
        },
        complete() {
          job.running = false;
        },
      });
  }

  onJobSuccessfully(job: Job, res: Response): void {
    job.status = res.status;
    job.running = false;
    this.logs.unshift({ job, res, err: <HttpErrorResponse>{} });
    if (job.id === 'databaseToFile') {
      this.loadTableFiles();
    }
  }

  onJobError(job: Job, err: HttpErrorResponse): void {
    console.error(err);
    job.status = 'Failed';
    job.running = false;
    this.logs.unshift({ job, res: <Response>{ status: job.status }, err });
  }

  loadTableFiles() {
    this.service
      .get<File[]>('/srv/file')
      .pipe(
        catchError((error) => {
          console.error(error);
          return of([]);
        })
      )
      .subscribe({
        next: (data) => (this.files = data),
      });
  }
}
