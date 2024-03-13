import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BookModel } from "../model/BookModel";
import { Observable } from "rxjs";

const baseUrl = 'http://localhost:8070/api';

@Injectable({
    providedIn : 'root'
})
export class BookService{

    constructor(private http: HttpClient) { }

    getAll(): Observable<BookModel[]> {
        return this.http.get<BookModel[]>(baseUrl);
    }

    create(data: BookModel): Observable<any> {
        return this.http.post(baseUrl, data);
    }

    update(id: any, data: any): Observable<any> {
        return this.http.put(`${baseUrl}/${id}`, data);
    }

    delete(id: any): Observable<any> {
        return this.http.delete(`${baseUrl}/${id}`);
    }
}