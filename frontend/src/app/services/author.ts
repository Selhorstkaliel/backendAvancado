import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Author {
  id?: number;
  name: string;
  cpf: string;
  annualIncome: number;
}

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  private apiUrl = '/api/v1/authors';

  constructor(private http: HttpClient) { }

  getAuthors(): Observable<Author[]> {
    return this.http.get<Author[]>(this.apiUrl);
  }

  saveAuthor(author: Author): Observable<Author> {
    return this.http.post<Author>(this.apiUrl, author);
  }

  deleteAuthor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
