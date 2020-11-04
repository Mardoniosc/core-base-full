import { BaseResourceModel } from 'src/app/shared';

export class User extends BaseResourceModel {
  constructor(
    public id?: number,
    public nome?: string,
    public email?: string,
    public senha?: string,
    public cpf?: string,
    public login?: string,
    public dataNascimento?: Date,
    public imagem?: string,
    public status?: number,
    public perfilId?: number
  ) {
    super();
  }

  static fromJson(jsonData: any): User {
    return Object.assign(new User(), jsonData);
  }
}
