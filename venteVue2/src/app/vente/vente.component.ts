import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';

import { VenteModel } from '../model/VenteModel';
import { VenteService } from '../service/VenteService';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ArticleService } from '../service/ArticleService'; 
import { ArticleModel } from '../model/ArticleModel'; 
import { UniteService } from '../service/UniteService'; 
import { UniteModel } from '../model/UniteModel'; 


@Component({
  selector: 'app-vente',
  standalone: true,
  templateUrl: './vente.component.html',
  styleUrls: ['./vente.component.css'],
  imports : [CommonModule,ReactiveFormsModule]
})
export class VenteComponent {
  venteForm!: FormGroup; // Définir un FormGroup pour gérer le formulaire
  listeVente !: VenteModel[];
  vente !: VenteModel;
  listeArticle!: Observable<ArticleModel[]>; 
listeUnite!: Observable<UniteModel[]>; 


  page : number = 0;
  size : number = 10;
  nombrePage !: Array<number>;

  constructor(private formBuilder: FormBuilder, private venteService: VenteService,private router:Router,private articleService: ArticleService,private uniteService: UniteService) {
    this.venteForm = this.formBuilder.group({
      id_article: ['', Validators.required], 
nombre: ['', Validators.required], 
date: ['', Validators.required], 
unite: ['', Validators.required], 

    });
  }

  ngOnInit() {
    this.getAllVentes();
    this.getAllArticle(); 
this.getAllUnite(); 

  }

  getAllVentes() {
      this.venteService.getAll(this.page, this.size).subscribe(
        data => {
          this.nombrePage = new Array(data.totalPages);
          this.listeVente = data.content;
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
      this.getAllVentes();
  }

  getAllArticle() {
      this.listeArticle = this.articleService.getAll(); 
    } 
getAllUnite() {
      this.listeUnite = this.uniteService.getAll(); 
    } 


  resetForm(): void {
      this.venteForm.reset(); // Réinitialiser le formulaire
  }

  submit() {
    if (this.venteForm.valid) {
      const id_vente = this.venteForm.get('id_vente')?.value; 
const id_article = this.venteForm.get('id_article')?.value; 
const nombre = this.venteForm.get('nombre')?.value; 
const date = this.venteForm.get('date')?.value; 
const unite = this.venteForm.get('unite')?.value; 

      this.venteService.create(id_article,nombre,date,unite).subscribe({
        next: (response) => {
          console.log('Create successful:', response); // Affichez la réponse renvoyée par la méthode create
          this.getAllVentes();
        },
        error: (error) => {
          console.error('Error:', error); // Affichez les éventuelles erreurs
        }
      });
    }
  }

  deleteVente(id:number | undefined):void{
        this.venteService.delete(id).subscribe({
          next: (response) => {
            console.log('delete successful:', response); 
            this.getAllVentes();
          },
          error: (error) => {
            console.error('Error:', error); 
          }
        });
  }

  versUpdate(vente:VenteModel){
        this.vente = vente;

        this.venteForm.patchValue({
          idVente:vente.idVente,
id_article:vente.article.idArticle,
nombre:vente.nombre,
date:vente.date,
unite:vente.unite.idUnite,

        });
  }


  update() {
      if (this.venteForm.valid) {
        const id_vente = this.venteForm.get('id_vente')?.value; 
const id_article = this.venteForm.get('id_article')?.value; 
const nombre = this.venteForm.get('nombre')?.value; 
const date = this.venteForm.get('date')?.value; 
const unite = this.venteForm.get('unite')?.value; 

        const id = this.vente.idVente;
        this.venteService.update(id,id_article,nombre,date,unite).subscribe({
          next: (response) => {
            console.log('Create successful:', response); // Affichez la réponse renvoyée par la méthode create
            this.getAllVentes();
          },
          error: (error) => {
            console.error('Error:', error); // Affichez les éventuelles erreurs
          }
        });
      }
    }
}
