import { OnInit, AfterContentChecked, Injector } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { switchMap } from 'rxjs/operators';
import toastr from 'toastr';

import { BaseResourceModel } from '../../models';
import { BaseResourceService } from '../../services';

export abstract class BaseResourceFormComponent<T extends BaseResourceModel>
  implements OnInit, AfterContentChecked {
  currentAction: string;
  resourceForm: FormGroup;
  pageTitle: string;
  serverErrorMessages: string[] = null;
  submittingForm: boolean = false;

  protected route: ActivatedRoute;
  protected router: Router;
  protected formBuilder: FormBuilder;

  constructor(
    protected injecto: Injector,
    public resource: T,
    protected resourceService: BaseResourceService<T>,
    protected jsonDataToResourceFn: (jsonData) => T
  ) {
    this.route = this.injecto.get(ActivatedRoute);
    this.router = this.injecto.get(Router);
    this.formBuilder = this.injecto.get(FormBuilder);
  }

  ngOnInit(): void {
    this.setCurrentAction();
    this.buildResourceForm();
    this.loadResource();
  }

  ngAfterContentChecked() {
    this.setPageTitle();
  }

  submitForm() {
    this.submittingForm = true;

    if (this.currentAction === 'new') {
      this.createResource();
    } else {
      this.updateResource();
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

  private buildResourceForm() {
    this.resourceForm = this.formBuilder.group({
      id: [null],
      nome: [null, [Validators.required, Validators.minLength(2)]],
    });
  }

  private loadResource() {
    if (this.currentAction === 'edit') {
      this.route.paramMap
        .pipe(
          switchMap((parms) =>
            this.resourceService.getById(Number(parms.get('id')))
          )
        )
        .subscribe(
          (data) => {
            this.resource = data;
            this.resourceForm.patchValue(this.resource);
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
      this.pageTitle = this.createPageTitle();
    } else {
      this.pageTitle = this.editionPageTitle();
    }
  }

  protected createPageTitle(): string {
    return 'Novo';
  }

  protected editionPageTitle(): string {
    return 'Edição';
  }

  private createResource() {
    const resource: T = this.jsonDataToResourceFn(this.resourceForm.value);
    this.resourceService.create(resource).subscribe(
      (data) => {
        const locationHeader = data.headers.get('location');
        const id_resource = locationHeader.substring(
          locationHeader.lastIndexOf('/') + 1
        );
        this.resource.id = id_resource;
        this.actionForSuccess(this.resource);
      },
      (err) => this.actionForError(err)
    );
  }

  private updateResource() {
    const resource: T = this.jsonDataToResourceFn(this.resourceForm.value);
    this.resourceService.update(resource).subscribe(
      (resource) => this.actionForSuccess(resource),
      (err) => this.actionForError(err)
    );
  }

  private actionForSuccess(resource: T) {
    toastr.success('Solicitação processada com sucesso!');

    this.router
      .navigateByUrl('resources', { skipLocationChange: true })
      .then(() => this.router.navigate(['resources', resource.id, 'edit']));
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
