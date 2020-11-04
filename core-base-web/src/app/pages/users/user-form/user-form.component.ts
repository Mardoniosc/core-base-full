import { Component, OnInit, AfterContentChecked } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { switchMap } from 'rxjs/operators';
import toastr from 'toastr';
import { UsersService } from '../shared/users.service';
import { User } from '../shared/user.model';
import { Profile } from '../../profiles/shared/profile.model';
import { ProfileService } from '../../profiles/shared/profile.service';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
})
export class UserFormComponent implements OnInit, AfterContentChecked {
  currentAction: string;
  userForm: FormGroup;
  pageTitle: string;
  serverErrorMessages: string[] = null;
  submittingForm: boolean = false;

  user: User = new User();

  profiles: Profile[] = [];

  ptBR = {
    firstDayOfWeek: 0,
    dayNames: [
      'Domingo',
      'Segunda',
      'Terça',
      'Quarta',
      'Quinta',
      'Sexta',
      'Sábado',
    ],
    dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab'],
    dayNamesMin: ['Do', 'Se', 'Te', 'Qu', 'Qu', 'Se', 'Sa'],
    monthNames: [
      'Janeiro',
      'Fevereiro',
      'Março',
      'Abril',
      'Maio',
      'Junho',
      'Julho',
      'Agosto',
      'Setembro',
      'Outubro',
      'Novembro',
      'Dezembro',
    ],
    monthNamesShort: [
      'Jan',
      'Fev',
      'Mar',
      'Abr',
      'Mai',
      'Jun',
      'Jul',
      'Ago',
      'Set',
      'Out',
      'Nov',
      'Dez',
    ],
    today: 'Hoje',
    clear: 'Limpar',
  };

  constructor(
    private userService: UsersService,
    private profileService: ProfileService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.setCurrentAction();
    this.buildUserForm();
    this.loadProfiles();
    this.loadUser();
  }

  ngAfterContentChecked() {
    this.setPageTitle();
  }

  submitForm() {
    this.submittingForm = true;

    if (this.currentAction === 'new') {
      this.createUser();
    } else {
      this.updateUser();
    }
  }

  // PRIVATE METHODS

  private setCurrentAction() {
    if (this.route.snapshot.url[0].path === 'new') {
      this.currentAction = 'new';
    } else {
      this.currentAction = 'edit';
    }
  }

  private buildUserForm() {
    this.userForm = this.formBuilder.group({
      id: [null],
      nome: [null, [Validators.required, Validators.minLength(2)]],
      email: [null, [Validators.required, Validators.email]],
      cpf: [null, [Validators.required]],
      senha: [null, [Validators.required]],
      login: [null, [Validators.required]],
      dataNascimento: [null, [Validators.required]],
      status: [0],
      perfilId: [null, [Validators.required]],
    });
  }

  private loadProfiles() {
    this.profileService.getAll().subscribe(
      (data) => (this.profiles = data),
      (err) => console.error(err)
    );
  }

  private loadUser() {
    if (this.currentAction === 'edit') {
      this.route.paramMap
        .pipe(
          switchMap((parms) =>
            this.userService.getById(Number(parms.get('id')))
          )
        )
        .subscribe(
          (data) => {
            this.user = data;
            this.userForm.patchValue({
              nome: this.user.nome,
              id: this.user.id,
              email: this.user.email,
              cpf: this.user.cpf,
              login: this.user.login,
              status: this.user.status,
              perfilId: data['perfil'].id,
              dataNascimento: new Date(this.user.dataNascimento),
            });
          },
          (err) => {
            toastr.error('Erro ao carregar o usuario');
            console.error('Erro ao carregar o usuario => ', err);
          }
        );
    }
  }

  setPageTitle() {
    if (this.currentAction === 'new') {
      this.pageTitle = 'Cadastro de novo usuário';
    } else {
      const userName = this.user.nome || '';
      this.pageTitle = 'Editando usuário: ' + userName;
    }
  }

  private createUser() {
    this.userForm.get('status').setValue(this.userForm.status ? 1 : 0);
    const user: User = Object.assign(new User(), this.userForm.value);
    this.userService.create(user).subscribe(
      (data) => {
        const locationHeader = data.headers.get('location');
        const id_user = locationHeader.substring(
          locationHeader.lastIndexOf('/') + 1
        );
        this.user.id = id_user;
        this.actionForSuccess(this.user);
      },
      (err) => this.actionForError(err)
    );
  }

  private updateUser() {
    const user: User = Object.assign(new User(), this.userForm.value);
    this.userService.update(user).subscribe(
      (user) => this.actionForSuccess(user),
      (err) => this.actionForError(err)
    );
  }

  private actionForSuccess(user: User) {
    toastr.success('Solicitação processada com sucesso!');

    this.router
      .navigateByUrl('users', { skipLocationChange: true })
      .then(() => this.router.navigate(['users', user.id, 'edit']));
  }

  private actionForError(err) {
    toastr.error('Ocorreu um erro ao processar a sua solicitação!');

    this.submittingForm = false;

    if (err.status === 422) {
      this.serverErrorMessages = JSON.parse(err._body).erros;
    } else {
      this.serverErrorMessages = [
        'Falha na comunicação com o servidor. Favor tente mais tarde!',
      ];
    }
  }
}
