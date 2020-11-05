import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [
  {
    path: '', redirectTo: 'dashboard', pathMatch: 'full'
  },

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
      import('./pages/permitions/permitions.module').then(
        (m) => m.PermitionsModule
      ),
  },
  {
    path: 'historys',
    loadChildren: () =>
      import('./pages/historico-log/historico-log.module').then(
        (m) => m.HistoricoLogModule
      ),
  },
  {
    path: 'dashboard',
    loadChildren: () =>
      import('./pages/dashboard/dashboard.module').then(
        (m) => m.DashboardModule
      ),
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
