import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Member } from 'src/app/models/member.model';
import { Project } from 'src/app/models/project.model';
import { MemberService } from 'src/app/services/member.service';
import { ProjectService } from 'src/app/services/project.service';

@Component({
  selector: 'app-member-list',
  templateUrl: './member-list.component.html',
  styleUrls: ['./member-list.component.css']
})
export class MemberListComponent implements OnInit , OnDestroy{
  
  members : Member[] = [];
  newMember : Member;
  currentProject : Project = null;
  currentProjectSubscription :Subscription;
  private formBuilder : FormBuilder = new FormBuilder();
  form : any;


  constructor(
    private memberService : MemberService,
    private projectService : ProjectService,
    private router: Router
  ) { 
    this.form = this.formBuilder.group({
      role :'',
      level :''
    });
  }

  ngOnInit(): void {
    this.currentProjectSubscription = this.projectService.currentProjectSubject.subscribe(
      (project: Project) => {
          if (project === null) {
              this.router.navigate(['']);
              return;
          }
          this.currentProject = Project.fromJSON(project);
          this.updateMembers();
      }
  );

  this.projectService.emitCurrentProject();
  }

  ngOnDestroy(): void{
    this.currentProjectSubscription.unsubscribe();
  }

  updateMembers() : void {
    this.memberService.getMembers(this.currentProject.getId()).subscribe(
      result => {
          this.members = result.map(x => Member.fromJSON(x));
      }
  );
  }

  onSubmit(data) : void {
    this.newMember = new Member(this.currentProject.getId(), data.role, data.level);
    this.memberService.postMember(this.newMember).subscribe(
      () => {}
    );

  }

}
