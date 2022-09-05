import { NgModule } from '@angular/core';
import { CodePipe } from './pipes/code.pipe';

import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';

import { ErrorDialogComponent } from './components/error-dialog/error-dialog.component';
import { CodeDialogComponent } from './components/code-dialog/code-dialog.component';
import { AddressPipe } from './pipes/address.pipe';
import { CurriculumGradeItemsOrderPipe } from './pipes/curriculum-grade-items-order.pipe';

@NgModule({
  declarations: [
    CodePipe,
    AddressPipe,
    CurriculumGradeItemsOrderPipe,
    ErrorDialogComponent,
    CodeDialogComponent,
  ],
  imports: [CommonModule, MatDialogModule, MatButtonModule],
  exports: [
    CodePipe,
    AddressPipe,
    CurriculumGradeItemsOrderPipe,
    ErrorDialogComponent,
    CodeDialogComponent,
  ],
})
export class SharedModule {}
