import { Component, OnInit } from '@angular/core';
import { BookService, Book } from '../../services/book';
import { CommonModule } from '@angular/common';


@Component({
    selector: 'app-book-list',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './book-list.html',
    styleUrl: './book-list.css'
})
export class BookListComponent implements OnInit {
    books: Book[] = [];
    errorMessage: string = "";

    constructor(private bookService: BookService) { }

    ngOnInit(): void {
        this.bookService.getBooks().subscribe({
            next: (data) => {
                this.books = data;
                this.errorMessage = "";
            },
            error: (err) => {
                this.errorMessage = "Could not connect to the backend API. Please check if the Spring Boot server is running.";
                console.error('API Error:', err);
            }
        });
    }
}
