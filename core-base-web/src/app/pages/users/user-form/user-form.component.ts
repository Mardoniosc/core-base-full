import { Component, OnInit, Injector } from '@angular/core';
import { Validators } from '@angular/forms';

import { UsersService, User } from '../shared';
import { Profile, ProfileService } from '../../profiles/shared';
import { BaseResourceFormComponent } from 'src/app/shared/components';

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css'],
})
export class UserFormComponent
  extends BaseResourceFormComponent<User>
  implements OnInit {
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
    protected profileService: ProfileService,
    protected userService: UsersService,
    protected injector: Injector
  ) {
    super(injector, new User(), userService, User.fromJson);
  }

  ngOnInit() {
    this.loaderProfiles();
    super.ngOnInit();
  }

  protected loaderProfiles() {
    this.profileService.getAll().subscribe(
      (data) => (this.profiles = data),
      (err) => super.actionForError(err)
    );
  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
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

  protected populandoFormulario(data) {
    const usuario: User = data;
    this.resourceForm.patchValue({
      nome: usuario.nome,
      id: usuario.id,
      email: usuario.email,
      cpf: usuario.cpf,
      login: usuario.login,
      status: usuario.status,
      perfilId: data['perfil'].id,
      dataNascimento: new Date(usuario.dataNascimento),
    });
  }

  protected createPageTitle(): string {
    return 'Cadastro de Novo Perfil';
  }

  protected editionPageTitle(): string {
    const categoryName = this.resource.nome || '';
    return 'Editando Perfil: ' + categoryName;
  }
}
