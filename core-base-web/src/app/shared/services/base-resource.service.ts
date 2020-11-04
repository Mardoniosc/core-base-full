import { HttpClient } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';

import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { BaseResourceModel } from '../models';

export abstract class BaseResourceService<T extends BaseResourceModel> {
  protected http: HttpClient;

  constructor(
    protected apiPath: string,
    protected injector: Injector,
    protected jsonDataToResourceFn: (jsonData: any) => T
  ) {
    this.http = injector.get(HttpClient);
  }

  getAll(): Observable<T[]> {
    return this.http
      .get<T[]>(this.apiPath)
      .pipe(
        catchError(this.handleError),
        map(this.jsonDataToresources.bind(this))
      );
  }

  getById(id: number): Observable<T> {
    const url = `${this.apiPath}/${id}`;
    return this.http
      .get(url)
      .pipe(
        catchError(this.handleError),
        map(this.jsonDataToresource.bind(this))
      );
  }

  create(profile: T): Observable<any> {
    return this.http
      .post(this.apiPath, profile, {
        observe: 'response',
        responseType: 'text',
      })
      .pipe(catchError(this.handleError));
  }

  update(profile: T): Observable<T> {
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
  protected jsonDataToresources(jsonData: any[]): T[] {
    const profiles: T[] = [];
    jsonData.forEach((element) =>
      profiles.push(this.jsonDataToResourceFn(element))
    );
    return profiles;
  }

  protected jsonDataToresource(jsonData: any[]): T {
    return this.jsonDataToResourceFn(jsonData);
  }

  protected handleError(error: any): Observable<any> {
    console.log('Erro na requisição => ', error);
    return throwError(error);
  }
}
