import { Component, OnInit } from '@angular/core';
import { AuthorService, Author } from '../../services/author';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-author-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './author-list.html',
  styleUrls: ['./author-list.css']
})
export class AuthorListComponent implements OnInit {
  authors: Author[] = [];
  loading = true;
  errorMessage = '';

  constructor(private authorService: AuthorService) {}

  ngOnInit(): void {
    this.loadAuthors();
  }

  loadAuthors(): void {
    this.authorService.getAuthors().subscribe({
      next: (data) => {
        this.authors = data;
        this.loading = false;
      },
      error: (err) => {
        this.errorMessage = 'CRITICAL ERROR: Backend connection refused. API endpoint /api/v1/authors not found.';
        this.loading = false;
        console.error(err);
      }
    });
  }

  deleteAuthor(id: number | undefined): void {
    if (id) {
      this.authorService.deleteAuthor(id).subscribe(() => this.loadAuthors());
    }
  }
}
