import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../services/user/user.service";
import {User} from "../../../../entity/account/user.model";
import {GenericObject} from "../../../../entity/generic.object.model";

@Component({
    selector: 'app-user',
    templateUrl: './user.component.html',
    styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

    public totalUsers:Number;
    public userList:User[];
    private searchKey:string = "search_user";

    constructor(private userService:UserService) {
    }

    ngOnInit() {

        this.getTotalUsers();
    }

    getTotalUsers():void {

        this.userService.countAllUsers().subscribe(
            resp => {
                console.log(resp);
                this.totalUsers = resp.body;
            },
            error => console.log(error)
        );
    }

    searchUser(value:string):void {

        if(value !== ""){

            let genericObject:GenericObject = new GenericObject(value);

            this.userService.searchUser(genericObject)
                .subscribe(
                    resp => this.userList = resp.body,
                    error => console.log(error),
                    () => console.log(this.userList)
                );
        }
    }
}
