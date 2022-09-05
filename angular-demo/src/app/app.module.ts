import { NgModule } from '@angular/core';

import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatListModule } from '@angular/material/list';
import { MatDividerModule } from '@angular/material/divider';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';

import { SpringDataJpaComponent } from './spring-data-jpa/spring-data-jpa.component';
import { SpringBatchComponent } from './spring-batch/spring-batch.component';
import { PeopleListComponent } from './spring-data-jpa/people-list/people-list.component';
import { AdressesListComponent } from './spring-data-jpa/adresses-list/adresses-list.component';
import { CompaniesListComponent } from './spring-data-jpa/companies-list/companies-list.component';
import { SharedModule } from './shared/shared.module';
import { UniversitiesListComponent } from './spring-data-jpa/universities-list/universities-list.component';
import { CoursesListComponent } from './spring-data-jpa/courses-list/courses-list.component';
import { StudentsListComponent } from './spring-data-jpa/students-list/students-list.component';

@NgModule({
  declarations: [
    AppComponent,
    SpringDataJpaComponent,
    SpringBatchComponent,
    PeopleListComponent,
    AdressesListComponent,
    CompaniesListComponent,
    UniversitiesListComponent,
    CoursesListComponent,
    StudentsListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    SharedModule,
    MatToolbarModule,
    MatCardModule,
    MatButtonModule,
    MatTableModule,
    MatGridListModule,
    MatListModule,
    MatDividerModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    MatButtonToggleModule,
    MatIconModule,
    MatProgressBarModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
