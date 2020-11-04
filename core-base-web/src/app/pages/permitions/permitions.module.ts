import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PermitionsRoutingModule } from './permitions-routing.module';
import { PermitionFormComponent } from './permition-form/permition-form.component';
import { PermitionListComponent } from './permition-list/permition-list.component';


@NgModule({
  declarations: [PermitionFormComponent, PermitionListComponent],
  imports: [
    CommonModule,
    PermitionsRoutingModule
  ]
})
export class PermitionsModule { }
