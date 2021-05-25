import { Component, OnInit } from '@angular/core';
import {UserDto} from "../../authentication/model/UserDto";
import {Conference} from "../../presentations/model/conference";

@Component({
  selector: 'app-listener-page',
  templateUrl: './listener-page.component.html',
  styleUrls: ['./listener-page.component.css']
})
export class ListenerPageComponent implements OnInit {

  listenerName = "";
  conferenceName = "";
  reservationFee = "";
  boughtToken = "";
  localStorage;
  conference: Conference;
  disabledState = false;

  strikeCheckout:any = null;

  constructor() { }

  ngOnInit(): void {
    this.listenerName = JSON.parse(localStorage.getItem('current-user')).fullName;

    this.localStorage = localStorage;
    if (sessionStorage.getItem('conference') != null)
    {
      this.conference = JSON.parse(sessionStorage.getItem('conference'))
      console.log(this.conference)
      this.boughtToken = this.conference.title + "-" + this.listenerName

      if (localStorage.getItem(this.boughtToken) != undefined)
        this.disabledState = true;
    }

    this.stripePaymentGateway();

  }

  checkout(amount, tokenVar) {
    if (localStorage.getItem(this.boughtToken) == undefined) {
      const strikeCheckout = (<any>window).StripeCheckout.configure({
        key: 'pk_test_51Its4nFpSLECQuccxcYMRLsTDkybRx7vl2KfTfSbvl3updph1o1NeU3X12cJTD8Dgwg0f6O1DjC7mmd7dq8jO3yp00bBnBu754',
        locale: 'auto',
        token: function (stripeToken: any) {
          console.log(stripeToken);
          localStorage.setItem(tokenVar, stripeToken.id)
          alert('Stripe token generated!\n' + stripeToken.id + "\n Use this code when attending the conference.");
        }
      });

      strikeCheckout.open({
        name: 'CMS Manager',
        description: 'Payment widgets',
        amount: amount * 100
      });
      if (localStorage.getItem(this.boughtToken) != undefined)
        this.disabledState = true;
    }
    else
      this.disabledState = true;


  }

  stripePaymentGateway() {
    if(!window.document.getElementById('stripe-script')) {
      const scr = window.document.createElement("script");
      scr.id = "stripe-script";
      scr.type = "text/javascript";
      scr.src = "https://checkout.stripe.com/checkout.js";

      scr.onload = () => {
        this.strikeCheckout = (<any>window).StripeCheckout.configure({
          key: 'pk_test_51Its4nFpSLECQuccxcYMRLsTDkybRx7vl2KfTfSbvl3updph1o1NeU3X12cJTD8Dgwg0f6O1DjC7mmd7dq8jO3yp00bBnBu754',
          locale: 'auto',
          token: function (token: any) {
            console.log(token)
            alert('Payment via stripe successfull!');
          }
        });
      }
      window.document.body.appendChild(scr);
    }
  }

}
