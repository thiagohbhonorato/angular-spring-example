import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, first, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SpringBatchService {
  private readonly baseUrl = environment.apiBatch;

  constructor(private httpClient: HttpClient) {}

  post<T>(uri: string): Observable<T> {
    return this.httpClient.post<T>(this.baseUrl + uri, null).pipe(first());
  }

  get<T>(uri: string) {
    return this.httpClient.get<T>(this.baseUrl + uri).pipe(first());
  }
}
