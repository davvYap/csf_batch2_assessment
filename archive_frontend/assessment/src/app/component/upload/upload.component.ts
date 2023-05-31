import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PostService } from 'src/app/service/post.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css'],
})
export class UploadComponent implements OnInit {
  form!: FormGroup;
  @ViewChild('archiveFile')
  archiveFile!: ElementRef;
  bundleId!: string;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private postSvc: PostService
  ) {}

  ngOnInit(): void {
    this.form = this.createForm();
  }

  createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control('', [Validators.required]),
      title: this.fb.control('', [Validators.required]),
      comments: this.fb.control(''),
    });
  }

  cancel() {
    this.form.reset();
    this.archiveFile.nativeElement.value = '';
    this.router.navigate(['/']);
  }

  invalidForm(): boolean {
    return (
      this.form.invalid || this.archiveFile.nativeElement.files.length === 0
    );
  }

  post() {
    let formData = new FormData();
    formData.set('name', this.form.value.name);
    formData.set('title', this.form.value.title);
    formData.set('comments', this.form.value.comments);
    formData.set('file', this.archiveFile.nativeElement.files[0]);

    this.postSvc
      .postArchiveFile(formData)
      .then((res) => {
        console.log(res.bundleId);
        this.bundleId = res.bundleId;
        this.router.navigate(['/display', res.bundleId]);
      })
      .catch((err) => {
        alert(err.message);
      });
  }
}
