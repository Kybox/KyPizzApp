import {Component, OnInit} from '@angular/core';
import {DeliveryService} from "../../../../services/delivery/delivery.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DeliveryMethod} from "../../../../entity/order/delivery.method.model";
import {AppSettings} from "../../../../config/app.settings";
import {Router} from "@angular/router";

@Component({
    selector: 'app-delivery',
    templateUrl: './delivery.component.html',
    styleUrls: ['./delivery.component.css']
})
export class DeliveryComponent implements OnInit {

    public addForm: FormGroup;
    public deliveryMethodList: DeliveryMethod[];

    constructor(private deliveryService: DeliveryService,
                private formBuilder: FormBuilder,
                private router: Router) {

        this.buildForm();
    }

    ngOnInit() {

        this.loadDeliveryMethodList();
    }

    public checkAmount(evt): void {

        if (evt.target.value === "")
            this.addForm.controls["amount"].reset();
    }

    public submitDeliveryMethod(): void {

        let deliveryMethod: DeliveryMethod = <DeliveryMethod>this.addForm.value;

        this.deliveryService.create(deliveryMethod).subscribe(
            () => this.loadDeliveryMethodList(),
            error => {
                console.log(error);
                if (error.status === AppSettings.HTTP_CODE_UNAUTHORIZED)
                    this.router
                        .navigateByUrl("auth/login/" + btoa("admin"))
                        .then(null);
            }
        );
    }

    public liveDescriptionUpdate(evt:any, idx:number) :void {

        if(evt.target.value !== "")
            this.deliveryMethodList[idx].description = evt.target.value;
    }

    public liveAmountUpdate(evt:any, idx:number) :void {

        if(evt.target.value !== "")
            this.deliveryMethodList[idx].amount = evt.target.value;
    }

    public updateDeliveryMethod(idx:number) :void {

        this.deliveryService
            .update(this.deliveryMethodList[idx])
            .subscribe(
                () => this.loadDeliveryMethodList(),
                error => {
                    console.log(error);
                    if (error.status === AppSettings.HTTP_CODE_UNAUTHORIZED)
                        this.router
                            .navigateByUrl("auth/login/" + btoa("admin"))
                            .then(null);
                }
            );
    }

    public deleteDeliveryMethod(idx:number) :void {

        this.deliveryService
            .delete(this.deliveryMethodList[idx].id)
            .subscribe(
                resp => this.deliveryMethodList = resp.body,
                error => {
                    console.log(error);
                    if (error.status === AppSettings.HTTP_CODE_UNAUTHORIZED)
                        this.router
                            .navigateByUrl("auth/login/" + btoa("admin"))
                            .then(null);
                }
            );
    }

    private buildForm(): void {

        this.addForm = this.formBuilder.group({
            description: [null, Validators.required],
            amount: [null, Validators.required]
        });
    }

    private loadDeliveryMethodList(): void {

        this.deliveryService.findAll().subscribe(
            resp => this.deliveryMethodList = resp.body,
            error => console.log(error)
        );
    }
}
