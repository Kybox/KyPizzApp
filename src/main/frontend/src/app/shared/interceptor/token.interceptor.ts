import {Injectable} from "@angular/core";
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {Observable} from "rxjs";
import {JwtObject} from "../../entity/security/jwt.object.model";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

    constructor() {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        let jwtObject:JwtObject = AuthenticationService.getJwtObject();

        console.log("Interceptor token : " + JSON.stringify(jwtObject));

        if(jwtObject != null){
            let clonedRequest:HttpRequest<any> = request.clone({
                setHeaders: {
                    Authorization: "Bearer " + jwtObject.token
                }
            });

            return next.handle(clonedRequest);
        }
        else return next.handle(request);
    }
}
