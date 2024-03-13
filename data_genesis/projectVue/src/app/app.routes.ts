import { Routes } from '@angular/router';
import { AddBookComponent } from './add-book/add-book.component';
import { ListeBookComponent } from './liste-book/liste-book.component';
import { UpdateBookComponent } from './update-book/update-book.component';

export const routes: Routes = [
    {'path':"",component:AddBookComponent},
    {'path':"listeBook",component:ListeBookComponent},
    {'path':"versUpdateBook/:id",component:UpdateBookComponent}
];
