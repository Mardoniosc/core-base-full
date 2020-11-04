import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PermitionFormComponent } from './permition-form/permition-form.component';
import { PermitionListComponent } from './permition-list/permition-list.component';

const routes: Routes = [
  { path: '', component: PermitionListComponent },
  { path: 'new', component: PermitionFormComponent },
  { path: ':id/edit', component: PermitionFormComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PermitionsRoutingModule {}
