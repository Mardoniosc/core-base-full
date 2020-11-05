import { Component } from '@angular/core';
import { HistoryLogService, HistoryLog } from '../shared';
import { BaseResourceListComponent } from 'src/app/shared/components';

@Component({
  selector: 'app-history-list',
  templateUrl: './history-list.component.html',
  styleUrls: ['./history-list.component.css']
})
export class HistoryListComponent extends BaseResourceListComponent<HistoryLog> {
  constructor(protected historyLogService: HistoryLogService) {
    super(historyLogService)
  }
}
