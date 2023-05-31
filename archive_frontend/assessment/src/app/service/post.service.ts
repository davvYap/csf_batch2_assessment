import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, lastValueFrom } from 'rxjs';
import { response } from '../component/upload/models';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  constructor(private http: HttpClient) {}

  postArchiveFile(formData: FormData): Promise<response> {
    return lastValueFrom(
      this.http.post<response>('http://localhost:8080/upload', formData)
    );
  }
}
