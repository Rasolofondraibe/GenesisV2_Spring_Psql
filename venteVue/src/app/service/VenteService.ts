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

    getAll(): Observable<VenteModel[]> {
        const url = this.baseUrl+'/tocrudvente.do';
        return this.http.get<VenteModel[]>(url);
    }

    create(article: any,nombre: any,date: any): Observable<any> {
        const url = `${this.baseUrl}/insertvente.do?article=${article}&nombre=${nombre}&date=${date}`;
        return this.http.post(url, null);
    }

    update(idVente: any,article: any,nombre: any,date: any): Observable<any> {
        return this.http.post(`${this.baseUrl}/updatevente.do?idVente=${idVente}&article=${article}&nombre=${nombre}&date=${date}`, null);
    }

    delete(idVente:any): Observable<any> {
        return this.http.post(`${this.baseUrl}/deletevente.do?idVente=${idVente}`,null);
    }

    findById(id: number): Observable<VenteModel | undefined> {
        return this.getAll().pipe(
          map((ventes: VenteModel[]) => ventes.find(vente => vente.idVente === id))
        );
    }
}
