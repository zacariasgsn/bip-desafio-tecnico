import { Component, OnInit, ViewChild } from '@angular/core';
import { BeneficioService } from '../../services/beneficio.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-beneficio-list',
  templateUrl: './beneficio-list.component.html'
})
export class BeneficioListComponent implements OnInit {

  displayedColumns: string[] = [
    'nome',
    'descricao',
    'valor',
    'ativo',
    'acoes'
  ];

  dataSource = new MatTableDataSource<any>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private service: BeneficioService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar() {
    this.service.listar().subscribe({
      next: data => {
        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
      },
      error: () => {
        this.snackBar.open(
          'Erro ao carregar benefícios',
          'Fechar',
          { duration: 4000 }
        );
      }
    });
  }

  confirmarExclusao(id: number) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: {
        titulo: 'Confirmar exclusão',
        mensagem: 'Deseja realmente excluir este benefício?'
      }
    });

    dialogRef.afterClosed().subscribe(confirmado => {
      if (confirmado) {
        this.excluir(id);
      }
    });
  }

  excluir(id: number) {
    this.service.excluir(id).subscribe({
      next: () => {
        this.snackBar.open(
          'Benefício excluído com sucesso!',
          'OK',
          { duration: 3000 }
        );
        this.carregar();
      },
      error: err => {
        this.snackBar.open(
          err.error?.message || 'Erro ao excluir benefício',
          'Fechar',
          { duration: 5000 }
        );
      }
    });
  }
}
