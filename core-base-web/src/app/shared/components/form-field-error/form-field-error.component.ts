import { Component, OnInit, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-form-field-error',
  template: `
    <p class="text-danger">
      {{ errorMensage }}
    </p>
  `,
  styleUrls: ['./form-field-error.component.css']
})
export class FormFieldErrorComponent implements OnInit {
  @Input('form-control') formControl: FormControl;

  constructor() {}

  ngOnInit(): void {}

  get errorMensage(): string | null {
    return this.mostraMensagemDeErro() ? this.getMessageError() : null;
  }

  private mostraMensagemDeErro(): boolean {
    return this.formControl.invalid && this.formControl.touched;
  }

  private getMessageError(): string | null {
    if (this.formControl.errors.required) {
      return 'Dado Obrigatório';
    } else if (this.formControl.errors.minlength) {
      const requiredLength = this.formControl.errors.minlength.requiredLength;
      return `Deve ter no minimo ${requiredLength} caracteres`;
    } else if (this.formControl.errors.maxlength) {
      const requiredLength = this.formControl.errors.maxlength.requiredLength;
      return `Deve ter no máximo ${requiredLength} caracteres`;
    } else if (this.formControl.errors.email) {
      return 'Formato de e-mail inválido';
    } else {
      return null;
    }
  }
}
