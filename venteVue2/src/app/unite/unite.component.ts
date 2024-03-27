import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule } from '@angular/forms';

import { UniteModel } from '../model/UniteModel';
import { UniteService } from '../service/UniteService';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-unite',
  standalone: true,
  templateUrl: './unite.component.html',
  styleUrls: ['./unite.component.css'],
  imports : [CommonModule,ReactiveFormsModule]
})
export class UniteComponent {
  uniteForm!: FormGroup; // Définir un FormGroup pour gérer le formulaire
  listeUnite !: UniteModel[];
  unite !: UniteModel;
  

  page : number = 0;
  size : number = 10;
  nombrePage !: Array<number>;

  constructor(private formBuilder: FormBuilder, private uniteService: UniteService,private router:Router) {
    this.uniteForm = this.formBuilder.group({
      unite: ['', Validators.required], 

    });
  }

  ngOnInit() {
    this.getAllUnites();
    
  }

  getAllUnites() {
      this.uniteService.getAll(this.page, this.size).subscribe(
        data => {
          this.nombrePage = new Array(data.totalPages);
          this.listeUnite = data.content;
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
      this.getAllUnites();
  }

  

  resetForm(): void {
      this.uniteForm.reset(); // Réinitialiser le formulaire
  }

  submit() {
    if (this.uniteForm.valid) {
      const id_unite = this.uniteForm.get('id_unite')?.value; 
const unite = this.uniteForm.get('unite')?.value; 

      this.uniteService.create(unite).subscribe({
        next: (response) => {
          console.log('Create successful:', response); // Affichez la réponse renvoyée par la méthode create
          this.getAllUnites();
        },
        error: (error) => {
          console.error('Error:', error); // Affichez les éventuelles erreurs
        }
      });
    }
  }

  deleteUnite(id:number | undefined):void{
        this.uniteService.delete(id).subscribe({
          next: (response) => {
            console.log('delete successful:', response); 
            this.getAllUnites();
          },
          error: (error) => {
            console.error('Error:', error); 
          }
        });
  }

  versUpdate(unite:UniteModel){
        this.unite = unite;

        this.uniteForm.patchValue({
          idUnite:unite.idUnite,
unite:unite.unite,

        });
  }


  update() {
      if (this.uniteForm.valid) {
        const id_unite = this.uniteForm.get('id_unite')?.value; 
const unite = this.uniteForm.get('unite')?.value; 

        const id = this.unite.idUnite;
        this.uniteService.update(id,unite).subscribe({
          next: (response) => {
            console.log('Create successful:', response); // Affichez la réponse renvoyée par la méthode create
            this.getAllUnites();
          },
          error: (error) => {
            console.error('Error:', error); // Affichez les éventuelles erreurs
          }
        });
      }
    }
}
