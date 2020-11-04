import { NgModule } from '@angular/core';

import { SharedModule } from 'src/app/shared/shared.module';

import { PermitionsRoutingModule } from './permitions-routing.module';
import { PermitionListComponent } from './permition-list/permition-list.component';

import { PermitionFormComponent } from './permition-form/permition-form.component';

@NgModule({
  declarations: [PermitionFormComponent, PermitionListComponent],
  imports: [SharedModule, PermitionsRoutingModule],
})
export class PermitionsModule {}
