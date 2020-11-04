import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared';
import {
  PermitionFormComponent,
  PermitionListComponent,
  PermitionsRoutingModule,
} from './';

@NgModule({
  declarations: [PermitionFormComponent, PermitionListComponent],
  imports: [SharedModule, PermitionsRoutingModule],
})
export class PermitionsModule {}
