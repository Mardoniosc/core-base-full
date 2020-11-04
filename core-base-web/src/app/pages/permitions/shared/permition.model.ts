import { BaseResourceModel } from 'src/app/shared';

export class Permition extends BaseResourceModel {
  constructor(
    public id?: number,
    public descricao?: string,
    public url?: string,
    public permissaoPai?: Permition
  ) {
    super();
  }
}
