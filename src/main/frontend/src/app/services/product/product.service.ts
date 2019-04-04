import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {IProduct} from "../../entity/product.model";
import {createRequestOption} from "../../shared/util/request-util";
import {ICategory} from "../../entity/category.model";
import {AppSettings} from "../../config/app.settings";

type EntityResponseType = HttpResponse<IProduct>;
type EntityArrayResponseType = HttpResponse<IProduct[]>;

@Injectable({
    providedIn: 'root'
})
export class ProductService {

    constructor(protected http: HttpClient) {
    }

    uploadFile(file: FormData): Observable<HttpEvent<{}>> {

        const req = new HttpRequest(
            "POST",
            AppSettings.ADMIN_API + "product/file",
            file,
            {
                reportProgress: true,
                responseType: 'json'
            });
        return this.http.request(req);
    }

    create(product: IProduct): Observable<EntityResponseType> {
        return this.http
            .post<IProduct>(AppSettings.ADMIN_API + "product", product,
                {observe: "response"});
    }

    findAll(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProduct[]>(AppSettings.ADMIN_API + "product",
                {params: options, observe: "response"});
    }

    update(product:IProduct):Observable<EntityResponseType> {
        return this.http
            .put<IProduct>(AppSettings.ADMIN_API + "product", product,
                {observe: "response"});
    }

    findByCategory(category:ICategory):Observable<EntityArrayResponseType>{
        return this.http
            .post<IProduct[]>(AppSettings.PUBLIC_API + "products/by/category", category,
                {observe: "response"});
    }
}
