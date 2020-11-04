import { Injectable, Injector } from '@angular/core';
import { BaseResourceService } from 'src/app/shared';

import { User } from './user.model';

@Injectable({
  providedIn: 'root',
})
export class UsersService extends BaseResourceService<User> {
  constructor(protected injector: Injector) {
    super('/api/usuarios', injector, User.fromJson);
  }
}
