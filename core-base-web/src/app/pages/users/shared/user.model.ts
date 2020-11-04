export class User {
  constructor(
    public id: number,
    public nome: string,
    public email: string,
    public senha: string,
    public cpf: string,
    public login: string,
    public dataNascimento: Date,
    public imagem: string,
    public status: number,
    public perfilId: number
  ) {}
}
