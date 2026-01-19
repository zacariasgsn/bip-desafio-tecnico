import { Component, OnInit } from '@angular/core';
import { BeneficioService } from '../../services/beneficio.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TouchedErrorStateMatcher } from '../../shared/error-state.matcher';

@Component({
  selector: 'app-transferencia',
  templateUrl: './transferencia.component.html'
})
export class TransferenciaComponent implements OnInit {

  matcher = new TouchedErrorStateMatcher();
  beneficios: any[] = [];
  form!: FormGroup;
  carregando = false;

  constructor(
    private service: BeneficioService,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      fromId: [null, Validators.required],
      toId: [null, Validators.required],
      valor: [null, [Validators.required, Validators.min(0.01)]]
    });

    this.service.listar().subscribe(data => {
      this.beneficios = data;
    });
  }

  transferir(): void {

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { fromId, toId, valor } = this.form.value;

    if (fromId === toId) {
      this.snackBar.open(
        'Origem e destino não podem ser iguais',
        'Fechar',
        { duration: 4000 }
      );
      return;
    }

    this.carregando = true;

    this.service.transferir({ fromId, toId, valor }).subscribe({
      next: () => {
        this.snackBar.open(
          'Transferência realizada com sucesso!',
          'OK',
          { duration: 3000 }
        );

        this.form.reset();
        this.form.markAsPristine();
        this.form.markAsUntouched();

        this.carregando = false;
      },
      error: err => {
        this.snackBar.open(
          err.error?.message || 'Erro ao realizar transferência',
          'Fechar',
          { duration: 5000 }
        );
        this.carregando = false;
      }
    });
  }
}
