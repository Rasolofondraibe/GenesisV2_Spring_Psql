import { Component } from '@angular/core';
import { ArticleModel } from '../model/ArticleModel';
import { ArticleService } from '../service/ArticleService';
import { Observable } from 'rxjs';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-liste-article',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './liste-article.component.html',
  styleUrl: './liste-article.component.css'
})
export class ListeArticleComponent {
    listeArticle !: Observable<ArticleModel[]>;

    constructor(private articleService : ArticleService,private router:Router){
    }
  
    ngOnInit() {
      this.getAllArticles();
    }

    getAllArticles() {
      this.listeArticle = this.articleService.getAll(); 
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

    versUpdate(id:number | undefined):void{
        this.router.navigateByUrl(`/versUpdateArticle/${id}`);
    }

    
}
