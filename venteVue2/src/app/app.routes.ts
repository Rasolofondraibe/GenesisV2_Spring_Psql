import { Routes } from '@angular/router';
import { ArticleComponent } from './article/article.component'; 
import { UniteComponent } from './unite/unite.component'; 
import { VenteComponent } from './vente/vente.component'; 


export const routes: Routes = [
    {'path':"",component:ArticleComponent}, 
{'path':"unite",component:UniteComponent}, 
{'path':"vente",component:VenteComponent}, 

];
