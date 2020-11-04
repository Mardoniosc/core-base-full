import { Component, OnInit } from '@angular/core';
import { UsersService } from '../shared/users.service';
import { User } from '../shared/user.model';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
})
export class UserListComponent implements OnInit {
  users: User[] = [];

  constructor(private userService: UsersService) {}

  ngOnInit(): void {
    this.userService.getAll().subscribe(
      (users) => {
        this.users = users;
      },
      (err) => console.error(err)
    );
  }

  deleteUser(user: User) {
    const mustDelete = confirm('Deseja realmente excluir este usuário?');

    if (mustDelete) {
      this.userService.delete(user.id).subscribe(
        (data) => (this.users = this.users.filter((x) => x.id != user.id)),
        (err) => alert('Erro ao tentar excluir este usuário!')
      );
    }
  }
}
