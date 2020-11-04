import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';

import { UsersRoutingModule } from './users-routing.module';
import { UserFormComponent } from './user-form/user-form.component';
import { UserListComponent } from './user-list/user-list.component';

import { CalendarModule } from 'primeng/calendar';

@NgModule({
  declarations: [UserFormComponent, UserListComponent],
  imports: [SharedModule, UsersRoutingModule, CalendarModule],
})
export class UsersModule {}
