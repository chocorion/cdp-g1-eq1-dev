import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
  form: any;
  private formBuilder: FormBuilder = new FormBuilder();

  constructor() { }

  ngOnInit(): void {
      this.form = this.formBuilder.group({
        taskId: 0,
        title: '',
        usId: '',
        duration: '',
        status: '',
        parents: '',
        children: '',
        member: '',
    });
  }

  createTask(data: any): void {
    console.log("je suis une task");
  }

}
