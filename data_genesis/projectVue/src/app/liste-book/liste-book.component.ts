import { Component } from '@angular/core';
import { BookModel } from '../model/BookModel';
import { BookService } from '../service/BookService';
import { Observable } from 'rxjs';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';


@Component({
  selector: 'app-liste-book',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './liste-book.component.html',
  styleUrl: './liste-book.component.css'
})
export class ListeBookComponent {
    listeBook !: Observable<BookModel[]>;

    constructor(private bookService : BookService,private router:Router){
    }
  
    ngOnInit() {
      this.getAllBooks();
    }

    getAllBooks() {
      this.listeBook = this.bookService.getAll(); 
    }

    deleteBook(id:number | undefined):void{
        this.bookService.delete(id).subscribe({
          next: (response) => {
            console.log('delete successful:', response); 
            this.getAllBooks();
          },
          error: (error) => {
            console.error('Error:', error); 
          }
        });
    }

    versUpdate(id:number | undefined):void{
        this.router.navigateByUrl(`/versUpdateBook/${id}`);
    }
}
