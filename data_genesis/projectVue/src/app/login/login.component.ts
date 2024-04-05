import { Component } from '@angular/core';
import { AuthService } from '../authService';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
     loginForm!: FormGroup; 

    constructor(private formBuilder: FormBuilder,private authService : AuthService,private router:Router){
      this.loginForm = this.formBuilder.group({
        email: ['', Validators.required],
        password: ['', Validators.required]
      });
    }


    localverification(){
      if(this.loginForm.valid){
        const email= this.loginForm.get('email')?.value;
        const password= this.loginForm.get('password')?.value;
        this.authService.login(email,password);
        this.router.navigateByUrl(``);
      }else{
        this.router.navigateByUrl(`/login`);
      }
    }
}
