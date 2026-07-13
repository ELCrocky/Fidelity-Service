import { inject } from '@angular/core'
import { CanActivateChildFn, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

export const roleGuard: CanActivateChildFn = (route) => {
    const authService = inject(AuthService);
    const router = inject(Router);

    const allowedRoles = route.data['roles'] as string[] | undefined;

    if (!allowedRoles || allowedRoles.length === 0){
        console.error(`roleGuard: aucune règle de rôle defini pour ${route.url}`);
        return router.createUrlTree(['/login']);
    }

    if(authService.hasRole(...allowedRoles)) {
        return true;
    }

    return router.createUrlTree(['/login']);
}