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
import { Address } from 'src/app/shared/model/address';
import { SpringDataJPAService } from '../spring-data-jpa.service';

@Component({
  selector: 'app-adresses-list',
  templateUrl: './adresses-list.component.html',
  styleUrls: ['./adresses-list.component.css'],
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
export class AdressesListComponent implements OnInit {
  title = 'Address List';
  expandedElement: Address | null;
  displayedColumns: string[] = [
    'postalCode',
    'streetName',
    'buildingNumber',
    'complement',
    'neighborhood',
    'city',
    'stateOrRegion',
    'country',
  ];
  displayedColumnsWithExpand = [...this.displayedColumns, 'expand'];
  dataSource$: Observable<Address[]>;

  constructor(private service: SpringDataJPAService, public dialog: MatDialog) {
    this.expandedElement = null;
    this.dataSource$ = this.service.get<Address[]>('/adresses').pipe(
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
