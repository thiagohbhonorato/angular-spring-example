import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
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
  status: 'No executed' | 'Running' | 'Completed' | 'Success' | 'Failed';
  description: string;
  service: string;
  running: boolean;
}

interface Service {
  id: string;
  name: string;
  status: string;
}

interface Response {
  status: 'No executed' | 'Running' | 'Completed' | 'Success' | 'Failed';
  start_time: string;
  end_time: string;
  elapsed_time: string;
}

interface Log {
  job: Job;
  srv: Service;
  res: Response;
  err: HttpErrorResponse;
}

interface File {
  name: string;
  creation_date: string;
}

interface People {
  id: number;
  name: string;
  birthDate: Date;
  document: string;
  maleOrFemale: string;
  processingKey: string;
}

@Component({
  selector: 'app-spring-batch',
  templateUrl: './spring-batch.component.html',
  styleUrls: ['./spring-batch.component.css'],
})
export class SpringBatchComponent {
  jobs: Job[];
  logs: Log[];

  fileSelected: File | null;
  files: File[];
  filesColumns = ['selection', 'name', 'creation_date', 'download'];

  people: People[];
  peopleColumns = ['name', 'birthDate', 'document'];

  constructor(
    private service: SpringBatchService,
    private snackBar: MatSnackBar
  ) {
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
        service: '/job/file-to-database/{fileName}',
      },
    ];

    this.fileSelected = null;
    this.files = [];
    this.loadTableFiles();

    this.people = [];
    this.loadTablePeople();
  }

  runJob(job: Job): void {
    let uri = job.service;
    if (job.id === 'fileToDatabase') {
      if (this.fileSelected === null) {
        if (this.files.length > 0) {
          this.snackBar.open('Select a file', 'OK');
        } else {
          this.snackBar.open('There is no file to select', 'OK');
        }
        return;
      }

      uri = uri.replace('{fileName}', this.fileSelected.name);
    }

    job.status = 'Running';
    job.running = true;

    this.service
      .post<Response>(uri)
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

  _log(log: Log): void {
    this.logs.unshift(log);
  }

  onJobSuccessfully(job: Job, res: Response): void {
    job.status = res.status;
    job.running = false;
    this._log({
      job,
      res,
      srv: <Service>{},
      err: <HttpErrorResponse>{},
    });
    if (job.id === 'databaseToFile') {
      this.loadTableFiles();
    } else if (job.id === 'fileToDatabase') {
      this.loadTablePeople();
    }
  }

  onServiceSuccessfully(srv: Service): void {
    this._log({
      job: <Job>{},
      res: <Response>{ status: 'Success' },
      srv,
      err: <HttpErrorResponse>{},
    });
    if (srv.id === 'cleanFiles') {
      this.loadTableFiles();
    } else if (srv.id === 'cleanDatabase') {
      this.loadTablePeople();
    }
  }

  onJobError(job: Job, err: HttpErrorResponse): void {
    console.error(err);
    job.status = 'Failed';
    job.running = false;
    this._log({
      job,
      res: <Response>{ status: job.status },
      srv: <Service>{},
      err,
    });
  }

  onServiceError(srv: Service, err: HttpErrorResponse): void {
    console.error(err);
    srv.status = 'Error';
    this._log({
      job: <Job>{},
      res: <Response>{ status: srv.status },
      srv,
      err,
    });
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

  loadTablePeople() {
    this.service
      .get<People[]>('/srv/people')
      .pipe(
        catchError((error) => {
          console.error(error);
          return of([]);
        })
      )
      .subscribe({
        next: (data) => (this.people = data),
      });
  }

  cleanDatabase() {
    const srv = <Service>{
      id: 'cleanDatabase',
      name: 'Clean database',
    };

    this.service
      .delete('/srv/people')
      .pipe(
        catchError((error) => {
          console.error(error);
          return of([]);
        })
      )
      .subscribe({
        next: () => {
          this.onServiceSuccessfully(srv);
        },
      });
  }

  cleanFiles() {
    const srv = <Service>{
      id: 'cleanFiles',
      name: 'Clean files',
    };

    this.service
      .delete('/srv/file')
      .pipe(
        catchError((error) => {
          console.error(error);
          return of([]);
        })
      )
      .subscribe({
        next: () => {
          this.onServiceSuccessfully(srv);
        },
      });
  }

  cleanLog() {
    this.logs = [];
  }

  fileDownload(file: File): void {
    this.service.download(`/srv/file/${file.name}`, file.name);
  }
}
