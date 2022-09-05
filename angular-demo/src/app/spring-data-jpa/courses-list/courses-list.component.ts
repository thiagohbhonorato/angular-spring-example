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
import { Course } from 'src/app/shared/model/course';
import { CurriculumGrade } from 'src/app/shared/model/curriculum-grade';
import { CurriculumGradeItem } from 'src/app/shared/model/curriculum-grade-item';
import { SpringDataJPAService } from '../spring-data-jpa.service';

@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrls: ['./courses-list.component.css'],
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
export class CoursesListComponent implements OnInit {
  title = 'Courses List';
  expandedElement: Course | null;
  displayedColumns: string[] = ['id', 'name', 'university'];
  displayedColumnsWithExpand = [...this.displayedColumns, 'expand'];
  displayedColumnsCurriculumGrade: string[] = ['stage', 'subject', 'workload'];
  dataSource$: Observable<Course[]>;

  curriculumGradeActive(
    curriculumGrades: CurriculumGrade[]
  ): CurriculumGradeItem[] {
    let data: CurriculumGrade | undefined = curriculumGrades.find(
      (item) => item.status === 'active'
    );
    return data?.curriculumGradeItems || [];
  }

  constructor(private service: SpringDataJPAService, public dialog: MatDialog) {
    this.expandedElement = null;
    this.dataSource$ = this.service.get<Course[]>('/courses').pipe(
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
