import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable, throwError} from "rxjs";
import {createRequestOption} from "../../shared/util/request-util";
import {ICategory} from "../../entity/category.model";
import {AppSettings} from "../../config/app.settings";
import {AuthenticationService} from "../authentication/authentication.service";

type BooleanResponseType = HttpResponse<Boolean>;
type EntityResponseType = HttpResponse<ICategory>;
type EntityArrayResponseType = HttpResponse<ICategory[]>;

@Injectable({
    providedIn: 'root'
})
export class CategoryService {

    constructor(protected http: HttpClient) {
    }

    findAll(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICategory[]>(AppSettings.PUBLIC_CATEGORY_API,
                {params: options, observe: "response"});
    }

    findById(id: string): Observable<HttpResponse<ICategory>> {
        return this.http
            .get<ICategory>(
                AppSettings.PUBLIC_CATEGORY_API + id,
                {observe: "response"});
    }

    create(category: ICategory): Observable<EntityResponseType> {

        let jwtObj = AuthenticationService.getJwtObject();

        if (jwtObj != null) {
            return this.http
                .post<ICategory>(AppSettings.ADMIN_CATEGORY_API,
                    category, {observe: "response"});

        } else return throwError(new Error(AppSettings.ERROR_UNAUTHORIZED))
    }

    update(category: ICategory): Observable<EntityResponseType> {

        let jwtObj = AuthenticationService.getJwtObject();

        if (jwtObj != null) {

            return this.http
                .put<ICategory>(AppSettings.ADMIN_CATEGORY_API,
                    category, {observe: "response"});

        } else return throwError(new Error(AppSettings.ERROR_UNAUTHORIZED));
    }

    delete(id: string): Observable<BooleanResponseType> {

        id = "/" + id;
        let jwtObj = AuthenticationService.getJwtObject();

        if (jwtObj != null) {
            return this.http
                .delete<any>(AppSettings.ADMIN_CATEGORY_API + id,
                    {observe: "response"});

        } else return throwError(new Error(AppSettings.ERROR_UNAUTHORIZED));
    }
}
