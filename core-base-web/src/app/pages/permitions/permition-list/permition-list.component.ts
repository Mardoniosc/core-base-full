import { Component } from '@angular/core';
import { Permition, PermitionService } from '../shared';
import { BaseResourceListComponent } from 'src/app/shared/components';

@Component({
  selector: 'app-permition-list',
  templateUrl: './permition-list.component.html',
  styleUrls: ['./permition-list.component.css'],
})
export class PermitionListComponent extends BaseResourceListComponent<Permition> {
  constructor(protected permitionService: PermitionService) {
    super(permitionService)
  }
}
