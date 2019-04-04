import {Component, OnInit} from '@angular/core';
import {AccountService} from "../../../../services/account/account.service";
import {User} from "../../../../entity/account/user.model";

@Component({
    selector: 'app-account',
    templateUrl: './account.component.html',
    styleUrls: ['./account.component.css', '../../client.component.css']
})
export class AccountComponent implements OnInit {

    public user:User;

    constructor(private accountService:AccountService) {
    }

    ngOnInit() {

        this.getUserDetails();
    }

    getUserDetails() {

        this.accountService.getUserDetails().subscribe(
            resp => this.user = resp.body,
            error => console.log(error)
        );
    }
}
