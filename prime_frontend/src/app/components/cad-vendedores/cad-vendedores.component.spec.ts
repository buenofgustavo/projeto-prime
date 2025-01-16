import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadVendedoresComponent } from './cad-vendedores.component';

describe('CadClienteComponent', () => {
  let component: CadVendedoresComponent;
  let fixture: ComponentFixture<CadVendedoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CadVendedoresComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadVendedoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
