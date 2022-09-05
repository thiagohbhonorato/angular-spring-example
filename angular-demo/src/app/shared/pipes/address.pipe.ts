import { Pipe, PipeTransform } from '@angular/core';
import { Address } from '../model/address';

@Pipe({
  name: 'address',
})
export class AddressPipe implements PipeTransform {
  transform(value: Address, ...args: unknown[]): string {
    return [
      value.streetName,
      ', ',
      value.buildingNumber,
      ', ',
      value.neighborhood,
      ', ',
      value.city,
      '/',
      value.stateOrRegion,
      ' - ',
      value.postalCode,
      ' - ',
      value.country,
    ].join('');
  }
}
