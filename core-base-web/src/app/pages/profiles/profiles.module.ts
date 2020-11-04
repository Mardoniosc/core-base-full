import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfilesRoutingModule } from './profiles-routing.module';
import { ProfileFormComponent } from './profile-form/profile-form.component';
import { ProfileListComponent } from './profile-list/profile-list.component';


@NgModule({
  declarations: [ProfileFormComponent, ProfileListComponent],
  imports: [
    CommonModule,
    ProfilesRoutingModule
  ]
})
export class ProfilesModule { }
