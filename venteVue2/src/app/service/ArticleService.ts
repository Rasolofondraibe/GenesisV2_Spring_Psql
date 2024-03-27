import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ArticleModel } from "../model/ArticleModel";
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';



@Injectable({
    providedIn : 'root'
})
export class ArticleService{
    baseUrl = 'http://localhost:8080';

    constructor(private http: HttpClient) { }

    getAll(page:number,size:number): Observable<any> {
        const url = `${this.baseUrl}/tocrudarticle.do?page=${page}&size=${size}`;
        return this.http.get<any>(url);
    }

    create(nom: any): Observable<any> {
        const url = `${this.baseUrl}/insertarticle.do?nom=${nom}`;
        return this.http.post(url, null);
    }

    update(idArticle: any,nom: any): Observable<any> {
        return this.http.post(`${this.baseUrl}/updatearticle.do?idArticle=${idArticle}&nom=${nom}`, null);
    }

    delete(idArticle:any): Observable<any> {
        return this.http.post(`${this.baseUrl}/deletearticle.do?idArticle=${idArticle}`,null);
    }
}
