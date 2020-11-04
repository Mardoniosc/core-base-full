import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PermitionsRoutingModule } from './permitions-routing.module';
import { PermitionListComponent } from './permition-list/permition-list.component';

import { ReactiveFormsModule } from '@angular/forms';
import { PermitionFormComponent } from './permition-form/permition-form.component';

@NgModule({
  declarations: [PermitionFormComponent, PermitionListComponent],
  imports: [CommonModule, PermitionsRoutingModule, ReactiveFormsModule],
})
export class PermitionsModule {}
