import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {NavigationComponent} from "./components/navigation/navigation.component";
import {AppSettings} from "../../config/app.settings";

@Component({
    selector: 'app-client',
    templateUrl: './client.component.html',
    styleUrls: ['./client.component.css', './components/css/animate.css']
})
export class ClientComponent implements OnInit {

    @ViewChild(NavigationComponent)
    private navigation:NavigationComponent;

    public authenticated:boolean;

    constructor(private router: Router) {

        this.authenticated = false;
    }

    ngOnInit() {

        this.checkAuthentication();
        this.navigation.isAuthenticated(null);
    }

    componentAdded(evt):void {

        if(typeof evt.notify !== "undefined"){
            evt.notify.subscribe(
                resp => this.navigation.isAuthenticated(resp)
            )
        }
    }
    checkAuthentication(){

        let token:string = localStorage.getItem(AppSettings.JWT_STORAGE_KEY);
        if(token != null) this.navigation.isAuthenticated(token);
        else{
            token = sessionStorage.getItem(AppSettings.JWT_STORAGE_KEY);
            if(token != null) this.navigation.isAuthenticated(token);
        }
    }
}
