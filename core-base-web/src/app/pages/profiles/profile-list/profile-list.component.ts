import { Component, OnInit } from '@angular/core';
import { Profile } from '../shared/profile.model';
import { ProfileService } from '../shared/profile.service';

@Component({
  selector: 'app-profile-list',
  templateUrl: './profile-list.component.html',
  styleUrls: ['./profile-list.component.css'],
})
export class ProfileListComponent implements OnInit {
  profiles: Profile[] = [];

  constructor(private profileService: ProfileService) {}

  ngOnInit(): void {
    this.profileService.getAll().subscribe(
      (profiles) => {
        this.profiles = profiles;
      },
      (err) => console.error(err)
    );
  }

  deleteProfile(profile: Profile) {
    const mustDelete = confirm('Deseja realmente excluir este usuário?');

    if (mustDelete) {
      this.profileService.delete(profile.id).subscribe(
        (data) =>
          (this.profiles = this.profiles.filter((x) => x.id != profile.id)),
        (err) => alert('Erro ao tentar excluir este usuário!')
      );
    }
  }
}
