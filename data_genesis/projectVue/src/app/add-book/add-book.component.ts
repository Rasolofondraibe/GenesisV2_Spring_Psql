import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';

import { BookModel } from '../model/BookModel';
import { BookService } from '../service/BookService';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-book',
  standalone: true,
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css'],
  imports : [CommonModule,ReactiveFormsModule]
})
export class AddBookComponent {
  bookForm!: FormGroup; // Définir un FormGroup pour gérer le formulaire

  constructor(private formBuilder: FormBuilder, private bookService: BookService,private router:Router) {
    this.bookForm = this.formBuilder.group({
      nom_book: ['', Validators.required],
      page: ['', Validators.required],
      type: ['', Validators.required]
    });
  }

  submit() {
    if (this.bookForm.valid) {
      const bookData = this.bookForm.value as BookModel;
      this.bookService.create(bookData).subscribe({
        next: (response) => {
          console.log('Create successful:', response); // Affichez la réponse renvoyée par la méthode create
          this.router.navigateByUrl(`/listeBook`);
        },
        error: (error) => {
          console.error('Error:', error); // Affichez les éventuelles erreurs
        }
      });
    }
  }
}
