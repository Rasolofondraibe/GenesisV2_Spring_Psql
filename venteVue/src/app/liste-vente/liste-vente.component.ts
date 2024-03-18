import { Component } from '@angular/core';
import { VenteModel } from '../model/VenteModel';
import { VenteService } from '../service/VenteService';
import { Observable } from 'rxjs';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-liste-vente',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './liste-vente.component.html',
  styleUrl: './liste-vente.component.css'
})
export class ListeVenteComponent {
    listeVente !: Observable<VenteModel[]>;

    constructor(private venteService : VenteService,private router:Router){
    }
  
    ngOnInit() {
      this.getAllVentes();
    }

    getAllVentes() {
      this.listeVente = this.venteService.getAll(); 
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

    versUpdate(id:number | undefined):void{
        this.router.navigateByUrl(`/versUpdateVente/${id}`);
    }

    
}
