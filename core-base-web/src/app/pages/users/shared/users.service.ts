import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, throwError } from 'rxjs';
import { map, catchError, flatMap } from 'rxjs/operators';

import { User } from './user.model';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  private apiPath: string = '/api/usuarios';

  constructor(private http: HttpClient) {}

  getAll(): Observable<User[]> {
    return this.http
      .get<User[]>(this.apiPath)
      .pipe(catchError(this.handleError), map(this.jsonDataToUsers));
  }

  getById(id: number): Observable<User> {
    const url = `${this.apiPath}/${id}`;
    return this.http
      .get(url)
      .pipe(catchError(this.handleError), map(this.jsonDataToUser));
  }

  create(user: User): Observable<User> {
    return this.http
      .post(this.apiPath, user)
      .pipe(catchError(this.handleError), map(this.jsonDataToUser));
  }

  update(user: User): Observable<User> {
    const url = `${this.apiPath}/${user.id}`;
    return this.http.put(url, user).pipe(
      catchError(this.handleError),
      map(() => user)
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
  private jsonDataToUsers(jsonData: any[]): User[] {
    const categories: User[] = [];
    jsonData.forEach((element) => categories.push(element as User));
    return categories;
  }

  private jsonDataToUser(jsonData: any[]): User {
    return jsonData as unknown as User;
  }

  private handleError(error: any): Observable<any> {
    console.log('Erro na requisição => ', error);
    return throwError(error);
  }
}
