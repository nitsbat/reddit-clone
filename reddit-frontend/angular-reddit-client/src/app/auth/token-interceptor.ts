import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, BehaviorSubject } from 'rxjs';
import { AuthService } from './shared/auth.service';
import { catchError, switchMap } from 'rxjs/operators';
import { LoginResponse } from '../auth/login/login-response.payload';

@Injectable({
    providedIn: 'root'
})
export class TokenInterceptor implements HttpInterceptor {

    isTokenRefreshing = false;
    refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);
    constructor(public authService: AuthService) { }

    intercept(req: HttpRequest<any>,
        next: HttpHandler): Observable<any> {

        if (req.url.indexOf('login') !== -1
            || (req.url.indexOf('/api/posts/') !== -1)
            || (req.url.indexOf('/api/subreddit') !== -1)
        ) {
            const jwtToken = this.authService.getJwtToken();
            if (jwtToken) {
                return next.handle(this.addToken(req, jwtToken));
            }
        }
        return next.handle(req);
    }

    private addToken(req: HttpRequest<any>, jwtToken: string) {
        return req.clone({
            headers: req.headers.set('Authorization',
                'Bearer ' + jwtToken)
        });
    }

}