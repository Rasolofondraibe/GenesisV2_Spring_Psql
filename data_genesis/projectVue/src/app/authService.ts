import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() {}

  login(email:string,password:string): void {
    const id:number = 5;
    localStorage.setItem('token', id.toString());
  }
}
