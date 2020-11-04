import { Component, OnInit, AfterContentChecked } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { switchMap } from 'rxjs/operators';
import toastr from 'toastr';
import { Permition, PermitionService } from '../shared';
@Component({
  selector: 'app-permition-form',
  templateUrl: './permition-form.component.html',
  styleUrls: ['./permition-form.component.css'],
})
export class PermitionFormComponent implements OnInit, AfterContentChecked {
  currentAction: string;
  permitionForm: FormGroup;
  pageTitle: string;
  serverErrorMessages: string[] = null;
  submittingForm: boolean = false;

  permition: Permition = new Permition();

  permitions: Permition[] = [];
  constructor(
    private permitionService: PermitionService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.setCurrentAction();
    this.buildPermitionForm();
    this.loadPermitions();
    this.loadPermition();
  }

  ngAfterContentChecked() {
    this.setPageTitle();
  }

  submitForm() {
    this.submittingForm = true;

    if (this.currentAction === 'new') {
      this.createPermition();
    } else {
      this.updatePermition();
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

  private buildPermitionForm() {
    this.permitionForm = this.formBuilder.group({
      id: [null],
      descricao: [null, [Validators.required, Validators.minLength(2)]],
      url: [null, [Validators.required]],
      permissaoPai: [null],
    });
  }

  private loadPermition() {
    if (this.currentAction === 'edit') {
      this.route.paramMap
        .pipe(
          switchMap((parms) =>
            this.permitionService.getById(Number(parms.get('id')))
          )
        )
        .subscribe(
          (data) => {
            this.permition = data;
            this.permitionForm.patchValue({
              id: this.permition.id,
              descricao: this.permition.descricao,
              url: this.permition.url,
              permissaoPai: this.permition.permissaoPai
                ? this.permition.permissaoPai.id
                : null,
            });
          },
          (err) => {
            toastr.error('Erro ao carregar o usuario');
            console.error('Erro ao carregar o usuario => ', err);
          }
        );
    }
  }

  private loadPermitions() {
    this.permitionService.getAll().subscribe(
      (data) => (this.permitions = data),
      (err) => console.error(err)
    );
  }

  setPageTitle() {
    if (this.currentAction === 'new') {
      this.pageTitle = 'Cadastro de nova Permissão';
    } else {
      const permitionName = this.permition.descricao || '';
      this.pageTitle = 'Editando Permissão: ' + permitionName;
    }
  }

  private createPermition() {
    const permition: Permition = Object.assign(
      new Permition(),
      this.permitionForm.value
    );
    this.permitionService.create(permition).subscribe(
      (data) => {
        const locationHeader = data.headers.get('location');
        const id_permition = locationHeader.substring(
          locationHeader.lastIndexOf('/') + 1
        );
        this.permition.id = id_permition;
        this.actionForSuccess(this.permition);
      },
      (err) => this.actionForError(err)
    );
  }

  private updatePermition() {
    const permition: Permition = Object.assign(
      new Permition(),
      this.permitionForm.value
    );
    this.permitionService.update(permition).subscribe(
      (permition) => this.actionForSuccess(permition),
      (err) => this.actionForError(err)
    );
  }

  private actionForSuccess(permition: Permition) {
    toastr.success('Solicitação processada com sucesso!');

    this.router
      .navigateByUrl('permitions', { skipLocationChange: true })
      .then(() => this.router.navigate(['permitions', permition.id, 'edit']));
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
