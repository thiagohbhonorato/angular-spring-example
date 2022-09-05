import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SpringBatchComponent } from './spring-batch/spring-batch.component';
import { AdressesListComponent } from './spring-data-jpa/adresses-list/adresses-list.component';
import { CompaniesListComponent } from './spring-data-jpa/companies-list/companies-list.component';
import { CoursesListComponent } from './spring-data-jpa/courses-list/courses-list.component';
import { PeopleListComponent } from './spring-data-jpa/people-list/people-list.component';
import { SpringDataJpaComponent } from './spring-data-jpa/spring-data-jpa.component';
import { StudentsListComponent } from './spring-data-jpa/students-list/students-list.component';
import { UniversitiesListComponent } from './spring-data-jpa/universities-list/universities-list.component';

const routes: Routes = [
  {
    path: 'spring-data-jpa',
    component: SpringDataJpaComponent,
    children: [
      { path: 'people', component: PeopleListComponent },
      { path: 'adresses', component: AdressesListComponent },
      { path: 'companies', component: CompaniesListComponent },
      { path: 'universities', component: UniversitiesListComponent },
      { path: 'courses', component: CoursesListComponent },
      { path: 'students', component: StudentsListComponent },
    ],
  },
  { path: 'spring-batch', component: SpringBatchComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
