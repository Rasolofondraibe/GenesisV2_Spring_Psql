import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';

import { ArticleModel } from '../model/ArticleModel';
import { ArticleService } from '../service/ArticleService';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-add-article',
  standalone: true,
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.css'],
  imports : [CommonModule,ReactiveFormsModule]
})
export class AddArticleComponent {
  articleForm!: FormGroup; // Définir un FormGroup pour gérer le formulaire
  

  constructor(private formBuilder: FormBuilder, private articleService: ArticleService,private router:Router) {
    this.articleForm = this.formBuilder.group({
      nom: ['', Validators.required], 

    });
  }

  ngOnInit() {
    
  }

  

  submit() {
    if (this.articleForm.valid) {
      const id_article = this.articleForm.get('id_article')?.value; 
const nom = this.articleForm.get('nom')?.value; 

      this.articleService.create(nom).subscribe({
        next: (response) => {
          console.log('Create successful:', response); // Affichez la réponse renvoyée par la méthode create
          this.router.navigateByUrl(`/listeArticle`);
        },
        error: (error) => {
          console.error('Error:', error); // Affichez les éventuelles erreurs
        }
      });
    }
  }
}
