import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HistoryListComponent } from './history-list';


const routes: Routes = [
  { path: '', component: HistoryListComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HistoricoLogRoutingModule { }
