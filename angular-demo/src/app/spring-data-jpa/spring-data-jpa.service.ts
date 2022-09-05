import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { first } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SpringDataJPAService {
  private readonly baseUrl = environment.apiDataJPA;

  constructor(private httpClient: HttpClient) {}

  get<T>(uri: string) {
    return this.httpClient.get<T>(this.baseUrl + uri).pipe(first());
  }
}
