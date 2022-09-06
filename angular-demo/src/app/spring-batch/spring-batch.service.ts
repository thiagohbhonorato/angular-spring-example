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

  get<T>(uri: string, options = {}) {
    return this.httpClient.get<T>(this.baseUrl + uri, options).pipe(first());
  }

  delete(uri: string) {
    return this.httpClient.delete(this.baseUrl + uri).pipe(first());
  }

  download(uri: string, fileName: string) {
    this.get(uri, {
      responseType: 'blob' as 'json',
    }).subscribe((response: any) => {
      let dataType = response.type;
      let binaryData = [];
      binaryData.push(response);
      let downloadLink = document.createElement('a');
      downloadLink.href = window.URL.createObjectURL(
        new Blob(binaryData, { type: dataType })
      );

      downloadLink.setAttribute('download', fileName);
      document.body.appendChild(downloadLink);
      downloadLink.click();
    });
  }
}
