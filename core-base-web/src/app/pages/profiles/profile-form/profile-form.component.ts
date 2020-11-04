import { Component, Injector } from '@angular/core';
import { Validators } from '@angular/forms';
import { BaseResourceFormComponent } from 'src/app/shared/components';
import { Profile, ProfileService } from '../shared/';

@Component({
  selector: 'app-profile-form',
  templateUrl: './profile-form.component.html',
  styleUrls: ['./profile-form.component.css'],
})
export class ProfileFormComponent extends BaseResourceFormComponent<Profile> {
  constructor(
    protected profileService: ProfileService,
    protected injector: Injector
  ) {
    super(injector, new Profile(), profileService, Profile.fromJson);
  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      nome: [null, [Validators.required, Validators.minLength(2)]],
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
