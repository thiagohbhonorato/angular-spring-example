import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { catchError, Observable, of } from 'rxjs';
import { CodeDialogComponent } from 'src/app/shared/components/code-dialog/code-dialog.component';
import {
  ErrorDialogComponent,
  ErrorDialogData,
} from 'src/app/shared/components/error-dialog/error-dialog.component';
import { Person } from 'src/app/shared/model/people';
import { SpringDataJPAService } from '../spring-data-jpa.service';

@Component({
  selector: 'app-people-list',
  templateUrl: './people-list.component.html',
  styleUrls: ['./people-list.component.css'],
})
export class PeopleListComponent implements OnInit {
  title = 'People List';
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'address'];
  dataSource$: Observable<Person[]>;

  constructor(private service: SpringDataJPAService, public dialog: MatDialog) {
    this.dataSource$ = this.service.get<Person[]>('/people').pipe(
      catchError((error) => {
        console.error(error);
        this.onError(error);
        return of([]);
      })
    );
  }

  openJSON(): void {
    this.dataSource$.subscribe((data) => {
      this.dialog.open(CodeDialogComponent, {
        data,
        minWidth: '90vw',
      });
    });
  }

  onError(error: any) {
    this.dialog.open(ErrorDialogComponent, {
      data: <ErrorDialogData>{
        title: 'Error loading data',
        error,
      },
    });
  }

  ngOnInit(): void {}
}
