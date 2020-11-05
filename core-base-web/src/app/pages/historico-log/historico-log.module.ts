import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { HistoricoLogRoutingModule } from './historico-log-routing.module';
import { HistoryListComponent } from './history-list/history-list.component';
import { SharedModule } from 'src/app/shared';


@NgModule({
  declarations: [HistoryListComponent],
  imports: [
    CommonModule,
    HistoricoLogRoutingModule,
    SharedModule
  ]
})
export class HistoricoLogModule { }
