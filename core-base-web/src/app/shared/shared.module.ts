import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { BreadCrumbComponent, FormFieldErrorComponent } from './components';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [BreadCrumbComponent, FormFieldErrorComponent],
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  exports: [
    // Módulos
    CommonModule,
    ReactiveFormsModule,

    // Components
    BreadCrumbComponent,
    FormFieldErrorComponent,
  ],
})
export class SharedModule {}
