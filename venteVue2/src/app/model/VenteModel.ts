import { ArticleModel } from "./ArticleModel"
import { UniteModel } from "./UniteModel"

export class VenteModel{
    article:ArticleModel;
nombre:number; 
date:Date; 
unite:UniteModel;
idVente?:number 


    constructor(article:ArticleModel,nombre:number,date:Date,unite:UniteModel,idVente?:number){ 
this.article=article;
this.nombre=nombre;
this.date=date;
this.unite=unite;
this.idVente=idVente;

 } 

}
