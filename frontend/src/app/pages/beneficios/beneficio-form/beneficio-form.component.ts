import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BeneficioService } from '../../../services/beneficio.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-beneficio-form',
  templateUrl: './beneficio-form.component.html'
})
export class BeneficioFormComponent implements OnInit {

  form!: FormGroup;
  id?: number;
  isEdicao = false;
  carregando = false;

  constructor(
    private service: BeneficioService,
    private route: ActivatedRoute,
    protected router: Router,
    private fb: FormBuilder,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {

    this.form = this.fb.group({
      nome: ['', Validators.required],
      descricao: [''],
      valor: [null, [Validators.required, Validators.min(0.01)]],
      ativo: [true]
    });

    this.id = Number(this.route.snapshot.paramMap.get('id'));

    if (this.id) {
      this.isEdicao = true;
      this.carregarBeneficio();
    }
  }

  carregarBeneficio() {
    this.service.buscarPorId(this.id!).subscribe({
      next: data => this.form.patchValue(data),
      error: () => {
        this.snackBar.open('Erro ao carregar benefício', 'Fechar', {
          duration: 4000
        });
        this.router.navigate(['/']);
      }
    });
  }

  salvar() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.carregando = true;

    const request = this.form.value;

    const obs = this.isEdicao
      ? this.service.atualizar(this.id!, request)
      : this.service.criar(request);

    obs.subscribe({
      next: () => {
        this.snackBar.open(
          this.isEdicao
            ? 'Benefício atualizado com sucesso!'
            : 'Benefício criado com sucesso!',
          'OK',
          { duration: 3000 }
        );
        this.router.navigate(['/']);
      },
      error: err => {
        this.snackBar.open(
          err.error?.message || 'Erro ao salvar benefício',
          'Fechar',
          { duration: 5000 }
        );
        this.carregando = false;
      }
    });
  }
}
