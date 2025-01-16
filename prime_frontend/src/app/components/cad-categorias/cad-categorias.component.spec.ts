import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadCategoriasComponent } from './cad-categorias.component';

describe('CadClienteComponent', () => {
  let component: CadCategoriasComponent;
  let fixture: ComponentFixture<CadCategoriasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CadCategoriasComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadCategoriasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
