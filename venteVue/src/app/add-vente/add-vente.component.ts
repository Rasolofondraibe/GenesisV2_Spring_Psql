import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';

import { VenteModel } from '../model/VenteModel';
import { VenteService } from '../service/VenteService';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ArticleService } from '../service/ArticleService'; 
import { ArticleModel } from '../model/ArticleModel'; 


@Component({
  selector: 'app-add-vente',
  standalone: true,
  templateUrl: './add-vente.component.html',
  styleUrls: ['./add-vente.component.css'],
  imports : [CommonModule,ReactiveFormsModule]
})
export class AddVenteComponent {
  venteForm!: FormGroup; // Définir un FormGroup pour gérer le formulaire
  listeArticle!: Observable<ArticleModel[]>; 


  constructor(private formBuilder: FormBuilder, private venteService: VenteService,private router:Router,private articleService: ArticleService) {
    this.venteForm = this.formBuilder.group({
      id_article: ['', Validators.required], 
nombre: ['', Validators.required], 
date: ['', Validators.required], 

    });
  }

  ngOnInit() {
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

      this.venteService.create(id_article,nombre,date).subscribe({
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
