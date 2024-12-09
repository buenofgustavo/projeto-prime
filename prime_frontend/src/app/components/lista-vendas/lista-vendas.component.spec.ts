import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaVendasComponent } from './lista-vendas.component';

describe('ListaVendasComponent', () => {
  let component: ListaVendasComponent;
  let fixture: ComponentFixture<ListaVendasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListaVendasComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaVendasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
