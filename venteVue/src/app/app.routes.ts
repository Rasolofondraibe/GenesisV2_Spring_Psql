import { Routes } from '@angular/router';
import { AddArticleComponent } from './add-article/add-article.component'; 
import { ListeArticleComponent } from './liste-article/liste-article.component'; 
import { UpdateArticleComponent } from './update-article/update-article.component'; 
import { AddVenteComponent } from './add-vente/add-vente.component'; 
import { ListeVenteComponent } from './liste-vente/liste-vente.component'; 
import { UpdateVenteComponent } from './update-vente/update-vente.component'; 


export const routes: Routes = [
    {'path':"addArticle",component:AddArticleComponent}, 
{'path':"listeArticle",component:ListeArticleComponent}, 
{'path':"versUpdateArticle/:id",component:UpdateArticleComponent}, 
{'path':"addVente",component:AddVenteComponent}, 
{'path':"listeVente",component:ListeVenteComponent}, 
{'path':"versUpdateVente/:id",component:UpdateVenteComponent}, 

];
