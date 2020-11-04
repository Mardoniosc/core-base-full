import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: 'users',
    loadChildren: () =>
      import('./pages/users/users.module').then((m) => m.UsersModule),
  },
  {
    path: 'profiles',
    loadChildren: () =>
      import('./pages/profiles/profiles.module').then((m) => m.ProfilesModule),
  },
  {
    path: 'permitions',
    loadChildren: () =>
      import('./pages/permitions/permitions.module').then((m) => m.PermitionsModule),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
