import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  let isConnect  = localStorage.getItem('token');
  const router = inject(Router);
  if(isConnect!==null){
    return true;
  }
  router.navigate(['login']);
  return false;
};
