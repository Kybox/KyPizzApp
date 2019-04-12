import {Component, OnInit} from '@angular/core';
import {PaymentService} from "../../../../services/payment/payment.service";
import {Payment} from "../../../../entity/payment/payment.model";
import {Router} from "@angular/router";
import {AppSettings} from "../../../../config/app.settings";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
    selector: 'app-payment',
    templateUrl: './payment.component.html',
    styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

    public stateLabel: string;
    public classLabel: string;
    public paymentList: Payment[];
    public msgInfo: string;
    public payForm: FormGroup;

    constructor(private paymentService: PaymentService,
                private formBuilder: FormBuilder,
                private router: Router) {

        this.stateLabel = "Payment method disabled";
        this.classLabel = "state-label";
        this.msgInfo = "";

        this.initForm();
    }

    ngOnInit() {

        this.loadPaymentList();
    }

    public setPaymentState(evt): void {

        if (evt.target.checked) {
            this.stateLabel = "Payment method activated";
            this.classLabel = "state-label-active";
        } else {
            this.stateLabel = "Payment method disabled";
            this.classLabel = "state-label";
        }
    }

    public submitPayment(): void {

        let payment: Payment = <Payment>this.payForm.value;

        this.paymentService.create(payment).subscribe(
            resp => this.loadPaymentList(),
            error => {
                console.log(error);
                this.msgInfo = error;
            }
        );
    }

    public liveUpdateApiLabel(idx: number, evt): void {

        this.paymentList[idx].api = evt.target.value;
    }

    public liveUpdateApiActivated(idx: number, evt): void {

        this.paymentList[idx].activated = evt.target.checked;
    }

    public updatePayment(idx: number): void {

        this.paymentService.update(this.paymentList[idx]).subscribe(
            () => this.msgInfo = "Payment method updated",
            error => console.log(error)
        );
    }

    public deletePayment(idx: string): void {

        this.paymentService.delete(this.paymentList[idx].id).subscribe(
            () => {
                this.loadPaymentList();
                this.msgInfo = "Payment deleted."
            },
            error => {
                console.log(error);
                this.msgInfo = error;
            }
        );
    }

    private loadPaymentList(): void {

        this.paymentService.findAll().subscribe(
            resp => {
                this.paymentList = resp.body;
                this.msgInfo = "There are " + this.paymentList.length + " payment methods available"
            },
            error => {
                //console.log(error);
                if (error.status === AppSettings.HTTP_CODE_UNAUTHORIZED)
                    this.router
                        .navigateByUrl("auth/login/" + btoa("admin"))
                        .then(null);
            }
        );
    }

    private initForm(): void {

        this.payForm = this.formBuilder.group({
            api: [null, Validators.required],
            activated: [false]
        });
    }
}
