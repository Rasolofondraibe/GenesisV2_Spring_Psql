import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { BookService } from '../service/BookService';
import { BookModel } from '../model/BookModel';
@Component({
  selector: 'app-update-book',
  standalone: true,
  imports : [CommonModule,ReactiveFormsModule],
  templateUrl: './update-book.component.html',
  styleUrl: './update-book.component.css'
})
export class UpdateBookComponent {
    id!:number;
    bookForm!: FormGroup; // Définir un FormGroup pour gérer le formulaire

    constructor(private route: ActivatedRoute,private formBuilder: FormBuilder, private bookService: BookService,private router:Router){
      this.bookForm = this.formBuilder.group({
        nom_book: ['', Validators.required],
        page: ['', Validators.required],
        type: ['', Validators.required]
      });
    }

    ngOnInit(){
        this.id = +this.route.snapshot.params['id'];
    }

    submit() {
      if (this.bookForm.valid) {
        const bookData = this.bookForm.value as BookModel;
        this.bookService.update(this.id,bookData).subscribe({
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
