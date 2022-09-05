import {
  trigger,
  state,
  style,
  transition,
  animate,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable, catchError, of } from 'rxjs';
import { CodeDialogComponent } from 'src/app/shared/components/code-dialog/code-dialog.component';
import {
  ErrorDialogComponent,
  ErrorDialogData,
} from 'src/app/shared/components/error-dialog/error-dialog.component';
import { Company } from 'src/app/shared/model/company';
import { Student } from 'src/app/shared/model/student';
import { StudentEnrollment } from 'src/app/shared/model/student-enrollment';
import { SpringDataJPAService } from '../spring-data-jpa.service';

@Component({
  selector: 'app-students-list',
  templateUrl: './students-list.component.html',
  styleUrls: ['./students-list.component.css'],
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
export class StudentsListComponent implements OnInit {
  title = 'Students List';
  expandedStudent: Student | null;
  expandedEnrollment: StudentEnrollment | null;
  displayedStudentColumns: string[] = [
    'id',
    'personFirstName',
    'personLastName',
  ];
  displayedStudentColumnsWithExpand = [
    ...this.displayedStudentColumns,
    'expand',
  ];
  displayedEnrollmentColumns: string[] = [
    'enrollmentNumber',
    'courseName',
    'enrollmentDate',
    'status',
  ];
  displayedEnrollmentColumnsWithExpand = [
    ...this.displayedEnrollmentColumns,
    'expand',
  ];
  displayedCurriculumGradeItemColumns: string[] = [
    'stage',
    'subject',
    'workload',
  ];
  dataSource$: Observable<Company[]>;

  constructor(private service: SpringDataJPAService, public dialog: MatDialog) {
    this.expandedStudent = null;
    this.expandedEnrollment = null;
    this.dataSource$ = this.service.get<Company[]>('/students').pipe(
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
