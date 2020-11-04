import { NgModule } from '@angular/core';

import { ProfilesRoutingModule } from './profiles-routing.module';
import { ProfileFormComponent } from './profile-form/profile-form.component';
import { ProfileListComponent } from './profile-list/profile-list.component';

import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [ProfileFormComponent, ProfileListComponent],
  imports: [ProfilesRoutingModule, SharedModule],
})
export class ProfilesModule {}
