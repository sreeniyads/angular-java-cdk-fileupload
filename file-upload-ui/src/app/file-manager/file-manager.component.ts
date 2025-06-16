import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { S3Service } from '../s3.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-file-manager',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './file-manager.component.html',
})
export class FileManagerComponent {
  files: string[] = [];
  prefix: string = '';
  selectedFile: File | null = null;

  constructor(private s3Service: S3Service) {
    this.fetchFiles();
  }

  fetchFiles(): void {
    this.s3Service.list(this.prefix).subscribe((data) => {
      this.files = data;
    });
  }

  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  upload(): void {
    if (this.selectedFile) {
      this.s3Service.upload(this.selectedFile, this.prefix).subscribe(() => {
        this.fetchFiles();
      });
    }
  }

  download(file: string): void {
    this.s3Service.getDownloadUrl(file).subscribe((url) => {
      window.open(url, '_blank');
    });
  }

  openFolder(file: string): void {
    this.prefix = file + '/';
    this.fetchFiles();
  }

  isFolder(file: string): boolean {
    return file.endsWith('/');
  }
}
