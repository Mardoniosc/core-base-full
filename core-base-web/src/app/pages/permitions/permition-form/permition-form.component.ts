import { Component, Injector, OnInit } from '@angular/core';
import { Validators } from '@angular/forms';
import { BaseResourceFormComponent } from 'src/app/shared/components';

import { Permition, PermitionService } from '../shared';

@Component({
  selector: 'app-permition-form',
  templateUrl: './permition-form.component.html',
  styleUrls: ['./permition-form.component.css'],
})
export class PermitionFormComponent
  extends BaseResourceFormComponent<Permition>
  implements OnInit {
  permitions: Permition[] = [];

  constructor(
    protected permitionService: PermitionService,
    protected injector: Injector
  ) {
    super(injector, new Permition(), permitionService, Permition.fromJson);
  }

  protected buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      descricao: [null, [Validators.required, Validators.minLength(2)]],
      url: [null, [Validators.required]],
      permissaoPai: [null],
    });
  }

  ngOnInit() {
    this.loadPermitions();
    super.ngOnInit();
  }

  protected loadPermitions() {
    this.permitionService.getAll().subscribe(
      (data) => (this.permitions = data),
      (err) => super.actionForError(err)
    );
  }

  protected createPageTitle(): string {
    return 'Cadastro de Nova Permissão';
  }

  protected editionPageTitle(): string {
    const categoryName = this.resource.descricao || '';
    return 'Editando Permissão: ' + categoryName;
  }
}
