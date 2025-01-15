import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadProdutosComponent } from './cad-produtos.component';

describe('CadClienteComponent', () => {
  let component: CadProdutosComponent;
  let fixture: ComponentFixture<CadProdutosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CadProdutosComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CadProdutosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
