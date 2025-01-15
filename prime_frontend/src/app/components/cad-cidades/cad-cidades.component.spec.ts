import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadCidadesComponent } from './cad-cidades.component';

describe('CadClienteComponent', () => {
  let component: CadCidadesComponent;
  let fixture: ComponentFixture<CadCidadesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CadCidadesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadCidadesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
