import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { GraficosComponent } from './graficos';


const routes: Routes = [
  { path: '', component: GraficosComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
