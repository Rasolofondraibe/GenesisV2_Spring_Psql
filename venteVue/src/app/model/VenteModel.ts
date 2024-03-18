import { ArticleModel } from "./ArticleModel"

export class VenteModel{
    article:ArticleModel;
nombre:number; 
date:Date; 
idVente?:number 


    constructor(article:ArticleModel,nombre:number,date:Date,idVente?:number){ 
this.article=article;
this.nombre=nombre;
this.date=date;
this.idVente=idVente;

 } 

}
