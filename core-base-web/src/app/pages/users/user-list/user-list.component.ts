import { Component } from '@angular/core';
import { UsersService, User } from '../shared';
import { BaseResourceListComponent } from 'src/app/shared/components';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent extends BaseResourceListComponent<User> {
  constructor(protected userService: UsersService) {
    super(userService)
  }
}
