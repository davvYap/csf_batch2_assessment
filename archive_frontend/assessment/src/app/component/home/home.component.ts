import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { archiveBundle } from '../upload/models';
import { GetService } from 'src/app/service/get.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  archiveBundle$!: Observable<archiveBundle[]>;

  constructor(private getSvc: GetService) {}

  ngOnInit(): void {
    this.archiveBundle$ = this.getSvc.getArchiveBundles();
  }
}
