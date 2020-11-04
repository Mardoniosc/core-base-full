export class Permition {
  constructor(
    public id?: number,
    public descricao?: string,
    public url?: string,
    public permissaoPai?: Permition
  ) {}
}
