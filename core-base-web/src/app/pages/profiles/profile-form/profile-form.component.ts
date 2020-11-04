import { Component, OnInit, AfterContentChecked } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { switchMap } from 'rxjs/operators';
import toastr from 'toastr';
import { ProfileService } from '../shared/profile.service';
import { Profile } from '../shared/profile.model';
@Component({
  selector: 'app-profile-form',
  templateUrl: './profile-form.component.html',
  styleUrls: ['./profile-form.component.css'],
})
export class ProfileFormComponent implements OnInit {
  currentAction: string;
  profileForm: FormGroup;
  pageTitle: string;
  serverErrorMessages: string[] = null;
  submittingForm: boolean = false;

  profile: Profile = new Profile();

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
    private profileService: ProfileService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.setCurrentAction();
    this.buildProfileForm();
    this.loadProfile();
  }

  ngAfterContentChecked() {
    this.setPageTitle();
  }

  submitForm() {
    this.submittingForm = true;

    if (this.currentAction === 'new') {
      this.createProfile();
    } else {
      this.updateProfile();
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

  private buildProfileForm() {
    this.profileForm = this.formBuilder.group({
      id: [null],
      nome: [null, [Validators.required, Validators.minLength(2)]],
    });
  }

  private loadProfile() {
    if (this.currentAction === 'edit') {
      this.route.paramMap
        .pipe(
          switchMap((parms) =>
            this.profileService.getById(Number(parms.get('id')))
          )
        )
        .subscribe(
          (data) => {
            this.profile = data;
            this.profileForm.patchValue(this.profile);
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
      this.pageTitle = 'Cadastro de novo Perfil';
    } else {
      const profileName = this.profile.nome || '';
      this.pageTitle = 'Editando Perfil: ' + profileName;
    }
  }

  private createProfile() {
    const profile: Profile = Object.assign(
      new Profile(),
      this.profileForm.value
    );
    this.profileService.create(profile).subscribe(
      (data) => {
        const locationHeader = data.headers.get('location');
        const id_profile = locationHeader.substring(
          locationHeader.lastIndexOf('/') + 1
        );
        this.profile.id = id_profile;
        this.actionForSuccess(this.profile);
      },
      (err) => this.actionForError(err)
    );
  }

  private updateProfile() {
    const profile: Profile = Object.assign(
      new Profile(),
      this.profileForm.value
    );
    this.profileService.update(profile).subscribe(
      (profile) => this.actionForSuccess(profile),
      (err) => this.actionForError(err)
    );
  }

  private actionForSuccess(profile: Profile) {
    toastr.success('Solicitação processada com sucesso!');

    this.router
      .navigateByUrl('profiles', { skipLocationChange: true })
      .then(() => this.router.navigate(['profiles', profile.id, 'edit']));
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
