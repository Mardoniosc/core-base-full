import { Component } from '@angular/core';
import { Profile, ProfileService } from '../shared';
import { BaseResourceListComponent } from 'src/app/shared/components';

@Component({
  selector: 'app-profile-list',
  templateUrl: './profile-list.component.html',
  styleUrls: ['./profile-list.component.css'],
})
export class ProfileListComponent extends BaseResourceListComponent<Profile> {
  constructor(protected profileService: ProfileService) {
    super(profileService);
  }
}
