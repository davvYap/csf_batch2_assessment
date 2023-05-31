import { Component, OnInit } from '@angular/core';
import { GetService } from 'src/app/service/get.service';
import { archive } from '../upload/models';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.css'],
})
export class DisplayComponent implements OnInit {
  archive$!: Promise<archive>;
  bundleId!: string;

  constructor(
    private getSvc: GetService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.bundleId = this.activatedRoute.snapshot.params['id'];
    this.archive$ = this.getSvc.getArchive(this.bundleId);
  }

  back(): void {
    this.router.navigate(['/']);
  }
}
