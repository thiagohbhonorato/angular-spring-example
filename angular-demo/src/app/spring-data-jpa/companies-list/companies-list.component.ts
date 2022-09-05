import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { catchError, Observable, of } from 'rxjs';
import { CodeDialogComponent } from 'src/app/shared/components/code-dialog/code-dialog.component';
import {
  ErrorDialogComponent,
  ErrorDialogData,
} from 'src/app/shared/components/error-dialog/error-dialog.component';
import { Company } from 'src/app/shared/model/company';
import { SpringDataJPAService } from '../spring-data-jpa.service';

@Component({
  selector: 'app-companies-list',
  templateUrl: './companies-list.component.html',
  styleUrls: ['./companies-list.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      ),
    ]),
  ],
})
export class CompaniesListComponent implements OnInit {
  title = 'Companies List';
  expandedElement: Company | null;
  displayedColumns: string[] = ['id', 'name', 'segment', 'address'];
  displayedColumnsWithExpand = [...this.displayedColumns, 'expand'];
  displayedColumnsEmployee: string[] = [
    'id',
    'firstName',
    'lastName',
    'address',
  ];
  dataSource$: Observable<Company[]>;

  constructor(private service: SpringDataJPAService, public dialog: MatDialog) {
    this.expandedElement = null;
    this.dataSource$ = this.service.get<Company[]>('/companies').pipe(
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
