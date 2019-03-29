import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css', './components/css/animate.css']
})
export class ClientComponent implements OnInit {

  constructor(private router:Router) {
      console.log("url = " + this.router.url);
  }

  ngOnInit() {
  }

}
