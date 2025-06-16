import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class S3Service {
  private baseUrl = 'http://localhost:8080/api/files';

  constructor(private http: HttpClient) {}

  upload(file: File, path: string = ''): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('path', path);
    return this.http.post(`${this.baseUrl}/upload`, formData, {
      responseType: 'text',
    });
  }

  list(prefix: string = ''): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/list?prefix=${prefix}`);
  }

  getDownloadUrl(fileKey: string): Observable<string> {
    return this.http.get(`${this.baseUrl}/download?file=${fileKey}`, {
      responseType: 'text',
    });
  }
}
