import { Injectable, Injector } from '@angular/core';

import { Profile } from './profile.model';
import { BaseResourceService } from 'src/app/shared';

@Injectable({
  providedIn: 'root',
})
export class ProfileService extends BaseResourceService<Profile> {
  constructor(protected injector: Injector) {
    super('/api/perfils', injector, Profile.fromJson);
  }
}
