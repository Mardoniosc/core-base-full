import { BaseResourceModel } from '../../models';
import { OnInit } from '@angular/core';
import { BaseResourceService } from '../../services';

export abstract class BaseResourceListComponent<T extends BaseResourceModel>
  implements OnInit {
  resources: T[] = [];

  constructor(private resourceService: BaseResourceService<T>) {}

  ngOnInit(): void {
    this.resourceService.getAll().subscribe(
      (data) => {
        this.resources = data;
      },
      (err) => alert('Erro ao carregar lista ' + err.status)
    );
  }

  deleteResource(resource: T) {
    const mustDelete = confirm('Deseja realmente excluir este item?');

    if (mustDelete) {
      this.resourceService.delete(resource.id).subscribe(
        (data) =>
          (this.resources = this.resources.filter((x) => x.id != resource.id)),
        (err) => alert('Erro ao tentar excluir!')
      );
    }
  }
}
