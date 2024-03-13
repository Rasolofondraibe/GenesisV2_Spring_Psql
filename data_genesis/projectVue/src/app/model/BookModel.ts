export class BookModel{
    id?:number;
    nom_book!:string;
    page!:number;
    type!:string;

    constructor(nom_book:string,page:number,type:string,id?:number){
        this.id=id;
        this.nom_book = nom_book;
        this.page = page;
        this.type = type;
    }
}