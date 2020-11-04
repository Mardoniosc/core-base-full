import { Component, OnInit } from '@angular/core';
import { Permition } from '../shared/permition.model';
import { PermitionService } from '../shared/permition.service';

@Component({
  selector: 'app-permition-list',
  templateUrl: './permition-list.component.html',
  styleUrls: ['./permition-list.component.css'],
})
export class PermitionListComponent implements OnInit {
  permitions: Permition[] = [];

  constructor(private permitionService: PermitionService) {}

  ngOnInit(): void {
    this.permitionService.getAll().subscribe(
      (permitions) => {
        this.permitions = permitions;
      },
      (err) => console.error(err)
    );
  }

  deletePermition(permition: Permition) {
    const mustDelete = confirm('Deseja realmente excluir este usuário?');

    if (mustDelete) {
      this.permitionService.delete(permition.id).subscribe(
        (data) =>
          (this.permitions = this.permitions.filter(
            (x) => x.id != permition.id
          )),
        (err) => alert('Erro ao tentar excluir este usuário!')
      );
    }
  }
}
