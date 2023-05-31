import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { archive, archiveBundle } from '../component/upload/models';
import { Observable, lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GetService {
  constructor(private http: HttpClient) {}

  getArchive(id: string): Promise<archive> {
    return lastValueFrom(
      this.http.get<archive>(`http://localhost:8080/bundle/${id}`)
    );
  }

  getArchiveBundles(): Observable<archiveBundle[]> {
    return this.http.get<archiveBundle[]>('http://localhost:8080/bundles');
  }
}
