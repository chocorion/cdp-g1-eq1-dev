import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Member } from 'src/app/models/member.model';
import { MemberService } from 'src/app/services/member.service';

@Component({
  selector: 'app-member-item',
  templateUrl: './member-item.component.html',
  styleUrls: ['./member-item.component.css']
})
export class MemberItemComponent implements OnInit {
  @Input() currentMember: Member;
  @Input() currentProjectId: number;
  form: any;
  private formBuilder: FormBuilder = new FormBuilder();

  constructor(
    private memberService: MemberService
  ) {
    this.form = this.formBuilder.group({
      role: '',
      level: ''
    });
  }

  ngOnInit(): void {
  }

  deleteMember(): void {
    this.memberService.deleteMember(this.currentMember).subscribe(
      () => { }
    );
  }

  onSubmit(data): void {
    this.currentMember.setRole(data.role);
    this.currentMember.setLevel(data.level);
    this.currentMember.setProject(this.currentProjectId);
    this.memberService.updateMember(this.currentMember).subscribe(
      () => { }
    );

  }



}
