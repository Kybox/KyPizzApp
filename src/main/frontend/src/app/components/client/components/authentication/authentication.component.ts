import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ValueObj} from "../../../../shared/value.object";
import {LoginForm} from "../../../../entity/form/login.form.model";
import {RegisterForm} from "../../../../entity/form/register.form.model";
import {AuthenticationService} from "../../../../services/authentication/authentication.service";
import {ActivatedRoute, NavigationEnd, Router, RoutesRecognized} from "@angular/router";
import {filter, pairwise} from "rxjs/operators";

@Component({
    selector: 'app-authentication',
    templateUrl: './authentication.component.html',
    styleUrls: ['./authentication.component.css', '../../client.component.css']
})
export class AuthenticationComponent implements OnInit {

    @Output() notify:EventEmitter<string> = new EventEmitter();

    public loginForm: FormGroup;
    public registerForm: FormGroup;
    public registerPassMatch: boolean;
    public errorMessage: string;
    public hasSignUpError: boolean;
    public hasSignInError: boolean;
    public rememberClass: string;

    public beforeLogin:string;

    constructor(private formBuilder: FormBuilder,
                private authService: AuthenticationService,
                private router: Router,
                private route:ActivatedRoute) {

        this.hasSignInError = false;
        this.registerPassMatch = false;
        this.hasSignUpError = false;
    }

    ngOnInit() {

        this.buildLoginForm();
        this.buildRegisterForm();
        this.beforeLogin = atob(this.route.snapshot.paramMap.get("param"));
        console.log("param : " + this.beforeLogin);
    }

    buildRegisterForm(): void {

        this.registerForm = this.formBuilder.group({
            nickName: [ValueObj.EMPTY_STRING, Validators.required],
            email: [ValueObj.EMPTY_STRING, [Validators.required, Validators.email]],
            firstName: [ValueObj.EMPTY_STRING, Validators.required],
            lastName: [ValueObj.EMPTY_STRING, Validators.required],
            password1: [ValueObj.EMPTY_STRING, Validators.required],
            password2: [ValueObj.EMPTY_STRING, Validators.required]
        });
    }

    buildLoginForm(): void {

        this.loginForm = this.formBuilder.group({
            login: [ValueObj.EMPTY_STRING, Validators.required],
            password: [ValueObj.EMPTY_STRING, Validators.required],
            remember: [false]
        });
    }

    checkPassword(): void {

        let pass1: AbstractControl = this.registerForm.controls['password1'];
        let pass2: AbstractControl = this.registerForm.controls['password2'];

        if (pass1.value != "" && pass2.value != "") {

            this.registerPassMatch = pass1.value == pass2.value;
        }
    }

    submitRegisterForm() {

        let registerForm: RegisterForm = this.registerForm.value;
        console.log(registerForm);
        this.authService.register(registerForm).subscribe(
            resp => {
                console.log(resp)
            },
            error => {
                console.log(error);
                this.hasSignUpError = true;
                this.errorMessage = error.error.message;
            }
        );
    }

    submitLoginForm(): void {

        let loginForm: LoginForm = this.loginForm.value;
        console.log(loginForm);
        this.authService.login(loginForm).subscribe(
            resp => {
                this.notify.emit(resp.body.token);
                AuthenticationService.saveToken(resp.body, loginForm.remember);
                if(this.beforeLogin === "cart")
                    this.router.navigateByUrl("process/order/" + btoa("process1")).then(null);
                else this.router.navigate(["auth/account"]).then(null);
            },
            error => {
                console.log(error);
                this.hasSignInError = true;
                this.errorMessage = error.error.message;
            }
        );
    }

    checkAlert(): void {

        if (this.hasSignUpError) this.hasSignUpError = false;
        if (this.hasSignInError) this.hasSignInError = false;
    }

    rememberChange(evt): void {
        if (evt.target.checked) this.rememberClass = "remember-me-active";
        else this.rememberClass = "label"
    }
}
