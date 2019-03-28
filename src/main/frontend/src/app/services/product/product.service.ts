import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {IProduct} from "../../entity/product.model";
import {createRequestOption} from "../../shared/util/request-util";
import {ICategory} from "../../entity/category.model";

@Injectable({
    providedIn: 'root'
})
export class ProductService {

    constructor(protected http: HttpClient) {
    }

    uploadFile(file: FormData): Observable<HttpEvent<{}>> {

        const req = new HttpRequest(
            "POST",
            "http://localhost:8080/admin/product/file",
            file,
            {
                reportProgress: true,
                responseType: 'json'
            });
        return this.http.request(req);
    }

    create(product: IProduct): Observable<HttpResponse<IProduct>> {
        return this.http
            .post("http://localhost:8080/admin/product", product,
                {observe: "response"});
    }

    findAll(req?: any): Observable<HttpResponse<IProduct[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<ICategory[]>("http://localhost:8080/admin/product",
                {params: options, observe: "response"});
    }

    update(product:IProduct):Observable<HttpResponse<IProduct>> {
        return this.http
            .put<IProduct>("http://localhost:8080/admin/product", product,
                {observe: "response"});
    }

    findByCategory(category:ICategory):Observable<HttpResponse<IProduct[]>>{

        return this.http
            .post<IProduct[]>("http://localhost:8080/client/products/by/category", category,
                {observe: "response"});
    }
}
