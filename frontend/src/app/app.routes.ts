import { Routes } from '@angular/router';
import { AuthorListComponent } from './components/author-list/author-list';
import { BookListComponent } from './components/book-list/book-list';


export const routes: Routes = [
  { path: '', component: AuthorListComponent },
  { path: 'authors', component: AuthorListComponent },
  { path: 'books', component: BookListComponent }
];
