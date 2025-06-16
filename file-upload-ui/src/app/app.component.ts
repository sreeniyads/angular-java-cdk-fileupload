import { Component } from '@angular/core';
import { FileManagerComponent } from './file-manager/file-manager.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FileManagerComponent],
  template: '<app-file-manager></app-file-manager>',
})
export class AppComponent {}
