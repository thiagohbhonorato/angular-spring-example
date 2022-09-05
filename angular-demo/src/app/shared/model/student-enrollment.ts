import { CurriculumGrade } from './curriculum-grade';

export interface StudentEnrollment {
  enrollmentNumber: number;
  enrollmentDate: Date;
  status: string;
  curriculumGrade: CurriculumGrade;
}
