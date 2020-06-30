import { Component, OnInit } from '@angular/core';
import { CompanyService } from '../services/company.service';
import { Company } from '../models/company';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {

  companies: Company[];

  constructor(private companyService: CompanyService) {}

  ngOnInit() {
  }

  getCompanies() {
    this.companyService.getCompanies().subscribe((company: Company[]) => {
      this.companies = company;
    });
  }

}
