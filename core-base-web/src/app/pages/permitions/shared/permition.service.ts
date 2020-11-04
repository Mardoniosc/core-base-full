import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, throwError } from 'rxjs';
import { map, catchError, flatMap, tap } from 'rxjs/operators';

import { Permition } from './permition.model';

@Injectable({
  providedIn: 'root',
})
export class PermitionService {
  private apiPath: string = '/api/permissoes';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Permition[]> {
    return this.http
      .get<Permition[]>(this.apiPath)
      .pipe(catchError(this.handleError), map(this.jsonDataToPermitions));
  }

  getById(id: number): Observable<Permition> {
    const url = `${this.apiPath}/${id}`;
    return this.http
      .get(url)
      .pipe(catchError(this.handleError), map(this.jsonDataToPermition));
  }

  create(permition: Permition): Observable<any> {
    return this.http
      .post(this.apiPath, permition, {
        observe: 'response',
        responseType: 'text',
      })
      .pipe(catchError(this.handleError));
  }

  update(permition: Permition): Observable<Permition> {
    const url = `${this.apiPath}/${permition.id}`;
    return this.http.put(url, permition).pipe(
      catchError(this.handleError),
      map(() => permition)
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
  private jsonDataToPermitions(jsonData: any[]): Permition[] {
    const permitions: Permition[] = [];
    jsonData.forEach((element) => permitions.push(element as Permition));
    return permitions;
  }

  private jsonDataToPermition(jsonData: any[]): Permition {
    return (jsonData as unknown) as Permition;
  }

  private handleError(error: any): Observable<any> {
    console.log('Erro na requisição => ', error);
    return throwError(error);
  }
}
