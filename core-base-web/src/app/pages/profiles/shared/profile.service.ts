import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, throwError } from 'rxjs';
import { map, catchError, flatMap, tap } from 'rxjs/operators';

import { Profile } from './profile.model';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  private apiPath: string = '/api/perfils';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Profile[]> {
    return this.http
      .get<Profile[]>(this.apiPath)
      .pipe(catchError(this.handleError), map(this.jsonDataToProfiles));
  }

  getById(id: number): Observable<Profile> {
    const url = `${this.apiPath}/${id}`;
    return this.http
      .get(url)
      .pipe(catchError(this.handleError), map(this.jsonDataToProfile));
  }

  create(profile: Profile): Observable<any> {
    return this.http
      .post(this.apiPath, profile, {
        observe: 'response',
        responseType: 'text',
      })
      .pipe(catchError(this.handleError));
  }

  update(profile: Profile): Observable<Profile> {
    const url = `${this.apiPath}/${profile.id}`;
    return this.http.put(url, profile).pipe(
      catchError(this.handleError),
      map(() => profile)
    );
  }

  delete(id: number): Observable<any> {
    const url = `${this.apiPath}/${id}`;
    return this.http.delete(url).pipe(
      catchError(this.handleError),
      map(() => null)
    );
  }

  // PRIVATE METHODS
  private jsonDataToProfiles(jsonData: any[]): Profile[] {
    const profiles: Profile[] = [];
    jsonData.forEach((element) => profiles.push(element as Profile));
    return profiles;
  }

  private jsonDataToProfile(jsonData: any[]): Profile {
    return (jsonData as unknown) as Profile;
  }

  private handleError(error: any): Observable<any> {
    console.log('Erro na requisição => ', error);
    return throwError(error);
  }
}
