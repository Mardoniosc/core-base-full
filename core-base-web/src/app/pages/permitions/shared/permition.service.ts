import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from 'src/app/shared';

import { Permition } from './permition.model';

@Injectable({
  providedIn: 'root',
})
export class PermitionService extends BaseResourceService<Permition> {
  constructor(protected injector: Injector) {
    super('/api/permissoes', injector, Permition.fromJson);
  }
}
