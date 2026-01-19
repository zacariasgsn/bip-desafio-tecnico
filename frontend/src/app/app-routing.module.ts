import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BeneficioListComponent } from './pages/beneficio-list/beneficio-list.component';
import {BeneficioFormComponent} from "./pages/beneficios/beneficio-form/beneficio-form.component";
import { TransferenciaComponent } from './pages/transferencia/transferencia.component';

const routes: Routes = [
  { path: '', component: BeneficioListComponent },
  { path: 'beneficios/novo', component: BeneficioFormComponent },
  { path: 'beneficios/editar/:id', component: BeneficioFormComponent },
  { path: 'beneficios/transferir', component: TransferenciaComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
