import { CurriculumGradeItem } from './curriculum-grade-item';

export interface CurriculumGrade {
  id: number;
  status: string;
  curriculumGradeItems: CurriculumGradeItem[];
}
