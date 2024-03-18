import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ArticleService } from '../service/ArticleService';
import { ArticleModel } from '../model/ArticleModel';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-update-article',
  standalone: true,
  imports : [CommonModule,ReactiveFormsModule],
  templateUrl: './update-article.component.html',
  styleUrl: './update-article.component.css'
})
export class UpdateArticleComponent {
    id!:number;
    articleForm!: FormGroup; // Définir un FormGroup pour gérer le formulaire
    

    constructor(private route: ActivatedRoute,private formBuilder: FormBuilder, private articleService: ArticleService,private router:Router){
      this.articleForm = this.formBuilder.group({
        nom: ['', Validators.required], 

      });
    }


    ngOnInit(){
        this.id = +this.route.snapshot.params['id'];
        
    }

    

    submit() {
      if (this.articleForm.valid) {
        const id_article = this.articleForm.get('id_article')?.value; 
const nom = this.articleForm.get('nom')?.value; 

        this.articleService.update(this.id,nom).subscribe({
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
