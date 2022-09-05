import { Person } from './people';
import { StudentEnrollment } from './student-enrollment';

export interface Student {
  id: number;
  person: Person;
  studentEnrollments: StudentEnrollment[];
}
