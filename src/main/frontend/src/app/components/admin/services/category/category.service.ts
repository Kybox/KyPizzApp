import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {createRequestOption} from "../../../../shared/util/request-util";
import {ICategory} from "../../../../entity/category.model";

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
            .get<ICategory[]>("http://localhost:8080/admin/category",
                {params: options, observe: "response"});
    }

    create(category:ICategory):Observable<EntityResponseType> {
        return this.http
            .post<ICategory>("http://localhost:8080/admin/category",
                category,
                { observe: "response"});
    }

    update(category:ICategory):Observable<EntityResponseType> {
        return this.http
            .put<ICategory>("http://localhost:8080/admin/category",
                category,
                {observe: "response"});
    }

    delete(id:string):Observable<BooleanResponseType> {
        return this.http
            .delete<any>("http://localhost:8080/admin/category/" + id,
                {observe: "response"});
    }
}
