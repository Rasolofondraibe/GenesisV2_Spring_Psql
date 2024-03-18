import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { VenteService } from '../service/VenteService';
import { VenteModel } from '../model/VenteModel';
import { Observable } from 'rxjs';
import { ArticleService } from '../service/ArticleService'; 
import { ArticleModel } from '../model/ArticleModel'; 


@Component({
  selector: 'app-update-vente',
  standalone: true,
  imports : [CommonModule,ReactiveFormsModule],
  templateUrl: './update-vente.component.html',
  styleUrl: './update-vente.component.css'
})
export class UpdateVenteComponent {
    id!:number;
    venteForm!: FormGroup; // Définir un FormGroup pour gérer le formulaire
    listeArticle!: Observable<ArticleModel[]>; 


    constructor(private route: ActivatedRoute,private formBuilder: FormBuilder, private venteService: VenteService,private router:Router,private articleService: ArticleService){
      this.venteForm = this.formBuilder.group({
        id_article: ['', Validators.required], 
nombre: ['', Validators.required], 
date: ['', Validators.required], 

      });
    }


    ngOnInit(){
        this.id = +this.route.snapshot.params['id'];
        this.getAllArticle();
    }

    getAllArticle() {
      this.listeArticle = this.articleService.getAll(); 
    }

    submit() {
      if (this.venteForm.valid) {
        const id_vente = this.venteForm.get('id_vente')?.value; 
const id_article = this.venteForm.get('id_article')?.value; 
const nombre = this.venteForm.get('nombre')?.value; 
const date = this.venteForm.get('date')?.value; 

        this.venteService.update(this.id,id_article,nombre,date).subscribe({
          next: (response) => {
            console.log('Create successful:', response); // Affichez la réponse renvoyée par la méthode create
            this.router.navigateByUrl(`/listeVente`);
          },
          error: (error) => {
            console.error('Error:', error); // Affichez les éventuelles erreurs
          }
        });
      }
    }
}
