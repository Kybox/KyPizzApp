import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ValueObj} from "../../../../shared/value.object";
import {LoginForm} from "../../../../entity/form/login.form.model";
import {RegisterForm} from "../../../../entity/form/register.form.model";

@Component({
    selector: 'app-authentication',
    templateUrl: './authentication.component.html',
    styleUrls: ['./authentication.component.css', '../../client.component.css']
})
export class AuthenticationComponent implements OnInit {

    public loginForm: FormGroup;
    public registerForm: FormGroup;
    public registerPassMatch:boolean;

    constructor(private formBuilder: FormBuilder) {

        this.registerPassMatch = false;
    }

    ngOnInit() {

        this.buildLoginForm();
        this.buildRegisterForm();
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
            password: [ValueObj.EMPTY_STRING, Validators.required]
        });
    }

    checkPassword() :void {

        let pass1:AbstractControl = this.registerForm.controls['password1'];
        let pass2:AbstractControl = this.registerForm.controls['password2'];

        if(pass1.value != "" && pass2.value != ""){

            if(pass1.value == pass2.value) this.registerPassMatch = true;
            else this.registerPassMatch = false;
        }
    }

    submitRegisterForm() {

        let registerForm:RegisterForm = this.registerForm.value;
        console.log(registerForm);
    }

    submitLoginForm(): void {

        let loginForm: LoginForm = this.loginForm.value;
        console.log(loginForm);
    }

}
