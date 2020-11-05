import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from 'src/app/shared';
import { HistoryLog } from './history-log.model';

@Injectable({
  providedIn: 'root'
})
export class HistoryLogService extends BaseResourceService<HistoryLog> {

  constructor(protected injector: Injector) {
    super('/api/historicoslog', injector, HistoryLog.fromJson);
  }
}
