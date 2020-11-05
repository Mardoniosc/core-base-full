import { BaseResourceModel } from 'src/app/shared';

export class HistoryLog extends BaseResourceModel {
  constructor(
    public id?: number,
    public dataAcesso?: Date,
    public usuarioId?: number,
    public loginIp?: string,
    public operacao?: string,
    public tabela?: string
  ) {
    super();
  }

  static fromJson(jsonData: any): HistoryLog {
    return Object.assign(new HistoryLog(), jsonData);
  }
}
