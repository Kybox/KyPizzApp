import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class FileService {

    constructor(private http: HttpClient) {
    }

    downloadFile(fileName: string): Observable<Blob> {

        return this.http
            .get(
                "http://localhost:8080/file/" + fileName,
                {responseType: "blob"});
    }

}
