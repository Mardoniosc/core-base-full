import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HistoricoLogRoutingModule } from './historico-log-routing.module';
import { HistoryListComponent } from './history-list/history-list.component';


@NgModule({
  declarations: [HistoryListComponent],
  imports: [
    CommonModule,
    HistoricoLogRoutingModule
  ]
})
export class HistoricoLogModule { }
