import { Address } from './address';
import { Person } from './people';

export interface Company {
  id: number;
  name: string;
  segment: string;
  address: Address;
  employees: Person[];
}
