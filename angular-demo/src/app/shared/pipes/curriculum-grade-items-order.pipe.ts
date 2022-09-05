import { Pipe, PipeTransform } from '@angular/core';
import { CurriculumGradeItem } from '../model/curriculum-grade-item';

@Pipe({
  name: 'curriculumGradeItemsOrder',
})
export class CurriculumGradeItemsOrderPipe implements PipeTransform {
  transform(
    value: CurriculumGradeItem[],
    ...args: unknown[]
  ): CurriculumGradeItem[] {
    return value.sort((a: CurriculumGradeItem, b: CurriculumGradeItem) => {
      if (a.stage === b.stage) {
        return a.subject.name > b.subject.name ? 1 : -1;
      } else return a.stage - b.stage;
    });
  }
}
