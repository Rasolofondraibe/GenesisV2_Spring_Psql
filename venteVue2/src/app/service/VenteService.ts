import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { VenteModel } from "../model/VenteModel";
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';



@Injectable({
    providedIn : 'root'
})
export class VenteService{
    baseUrl = 'http://localhost:8080';

    constructor(private http: HttpClient) { }

    getAll(page:number,size:number): Observable<any> {
        const url = `${this.baseUrl}/tocrudvente.do?page=${page}&size=${size}`;
        return this.http.get<any>(url);
    }

    create(article: any,nombre: any,date: any,unite: any): Observable<any> {
        const url = `${this.baseUrl}/insertvente.do?article=${article}&nombre=${nombre}&date=${date}&unite=${unite}`;
        return this.http.post(url, null);
    }

    update(idVente: any,article: any,nombre: any,date: any,unite: any): Observable<any> {
        return this.http.post(`${this.baseUrl}/updatevente.do?idVente=${idVente}&article=${article}&nombre=${nombre}&date=${date}&unite=${unite}`, null);
    }

    delete(idVente:any): Observable<any> {
        return this.http.post(`${this.baseUrl}/deletevente.do?idVente=${idVente}`,null);
    }
}
