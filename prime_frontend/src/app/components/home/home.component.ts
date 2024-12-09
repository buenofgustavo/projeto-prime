import { Component } from '@angular/core';
import { NbSidebarService } from '@nebular/theme';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  toggle() {
    this.sidebarService.toggle();
  }
  
  constructor(
    private sidebarService: NbSidebarService,
  ) {
    this.toggle()
  }

}
