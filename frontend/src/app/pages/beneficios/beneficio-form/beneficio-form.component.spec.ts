import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BeneficioFormComponent } from './beneficio-form.component';

describe('BeneficioFormComponent', () => {
  let component: BeneficioFormComponent;
  let fixture: ComponentFixture<BeneficioFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BeneficioFormComponent]
    });
    fixture = TestBed.createComponent(BeneficioFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
