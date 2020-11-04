import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProfileFormComponent } from './profile-form/profile-form.component';
import { ProfileListComponent } from './profile-list/profile-list.component';

const routes: Routes = [
  { path: '', component: ProfileListComponent },
  { path: 'new', component: ProfileFormComponent },
  { path: ':id/edit', component: ProfileFormComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProfilesRoutingModule {}
