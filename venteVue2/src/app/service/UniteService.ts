import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { UniteModel } from "../model/UniteModel";
import { Observable } from "rxjs";
import { map } from 'rxjs/operators';



@Injectable({
    providedIn : 'root'
})
export class UniteService{
    baseUrl = 'http://localhost:8080';

    constructor(private http: HttpClient) { }

    getAll(page:number,size:number): Observable<any> {
        const url = `${this.baseUrl}/tocrudunite.do?page=${page}&size=${size}`;
        return this.http.get<any>(url);
    }

    create(unite: any): Observable<any> {
        const url = `${this.baseUrl}/insertunite.do?unite=${unite}`;
        return this.http.post(url, null);
    }

    update(idUnite: any,unite: any): Observable<any> {
        return this.http.post(`${this.baseUrl}/updateunite.do?idUnite=${idUnite}&unite=${unite}`, null);
    }

    delete(idUnite:any): Observable<any> {
        return this.http.post(`${this.baseUrl}/deleteunite.do?idUnite=${idUnite}`,null);
    }
}
