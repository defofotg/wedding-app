import {Component, OnInit} from '@angular/core';
import {AuthService} from "../services/auth.service";
import {StorageService} from "../services/storage.service";
import {Router} from "@angular/router";
import {User} from "../models/user";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  error = {} as Error;
  constructor(
    private authService: AuthService,
    private storageService: StorageService,
    private toastrService: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.router.navigate(['/invitation']);
    }
  }

  onSubmit(form: any) {
    // implementation to handle form submission
    if (form?.form?.invalid) {
      return;
    }

    let user = form?.form?.value as User;
    console.log("user", user)

    this.authService.signIn(user).subscribe({
      next: (data) => {
        console.log(data);
        this.storageService.saveUser(data);
        this.toastrService.success('Connexion réussie!');
        this.router.navigate(['/invitation']);
      },
      error: (err) => {
        this.error.message = err;
        this.toastrService.error('Connexion échouée!');
      },
    });
  }
}
