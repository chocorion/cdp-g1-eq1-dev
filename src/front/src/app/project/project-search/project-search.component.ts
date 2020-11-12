import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-project-search',
  templateUrl: './project-search.component.html',
  styleUrls: ['./project-search.component.css']
})
export class ProjectSearchComponent implements OnInit {
   form = new FormGroup({
    search  : new FormControl('')
  });

  constructor() { }

  ngOnInit(): void {
  }
  onSubmit(){
    console.log(this.form.get('search'));
   }
   

} 

