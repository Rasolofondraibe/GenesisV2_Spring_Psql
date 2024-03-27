import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';

import { ArticleModel } from '../model/ArticleModel';
import { ArticleService } from '../service/ArticleService';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-article',
  standalone: true,
  templateUrl: './article.component.html',
  styleUrls: ['./article.component.css'],
  imports : [CommonModule,ReactiveFormsModule]
})
export class ArticleComponent {
  articleForm!: FormGroup; // Définir un FormGroup pour gérer le formulaire
  listeArticle !: ArticleModel[];
  article !: ArticleModel;
  

  page : number = 0;
  size : number = 10;
  nombrePage !: Array<number>;

  constructor(private formBuilder: FormBuilder, private articleService: ArticleService,private router:Router) {
    this.articleForm = this.formBuilder.group({
      nom: ['', Validators.required], 

    });
  }

  ngOnInit() {
    this.getAllArticles();
    
  }

  getAllArticles() {
      this.articleService.getAll(this.page, this.size).subscribe(
        data => {
          this.nombrePage = new Array(data.totalPages);
          this.listeArticle = data.content;
        }
      );
  }

  changePage(page:number){
      if(page===-1){
        page = this.page + 1;
      }else if(page===-2){
        page = this.page - 1;
      }

      if(page < 0 || page >= this.nombrePage.length){
         page = 0;
      }
      this.page = page;
      this.getAllArticles();
  }

  

  resetForm(): void {
      this.articleForm.reset(); // Réinitialiser le formulaire
  }

  submit() {
    if (this.articleForm.valid) {
      const id_article = this.articleForm.get('id_article')?.value; 
const nom = this.articleForm.get('nom')?.value; 

      this.articleService.create(nom).subscribe({
        next: (response) => {
          console.log('Create successful:', response); // Affichez la réponse renvoyée par la méthode create
          this.getAllArticles();
        },
        error: (error) => {
          console.error('Error:', error); // Affichez les éventuelles erreurs
        }
      });
    }
  }

  deleteArticle(id:number | undefined):void{
        this.articleService.delete(id).subscribe({
          next: (response) => {
            console.log('delete successful:', response); 
            this.getAllArticles();
          },
          error: (error) => {
            console.error('Error:', error); 
          }
        });
  }

  versUpdate(article:ArticleModel){
        this.article = article;

        this.articleForm.patchValue({
          idArticle:article.idArticle,
nom:article.nom,

        });
  }


  update() {
      if (this.articleForm.valid) {
        const id_article = this.articleForm.get('id_article')?.value; 
const nom = this.articleForm.get('nom')?.value; 

        const id = this.article.idArticle;
        this.articleService.update(id,nom).subscribe({
          next: (response) => {
            console.log('Create successful:', response); // Affichez la réponse renvoyée par la méthode create
            this.getAllArticles();
          },
          error: (error) => {
            console.error('Error:', error); // Affichez les éventuelles erreurs
          }
        });
      }
    }
}
