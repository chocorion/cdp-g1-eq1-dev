import { Component, OnInit, Version } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ReleaseVersion} from '../../../../models/releaseversion.model';
import {ProjectService} from '../../../../services/project.service';
import {ReleaseService} from '../../../../services/release.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { Release } from 'src/app/models/release.model';
import { Us } from 'src/app/models/us.model';
import { UsService } from 'src/app/services/us.service';
import { Project } from 'src/app/models/project.model';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-release-create-form',
  templateUrl: './release-create-form.component.html',
  styleUrls: ['./release-create-form.component.css']
})
export class ReleaseCreateFormComponent implements OnInit {
  releaseForm: FormGroup;

  unreleased: Us[] = [];
  possibleUserStories: Us[] = [];
  toAddUserStories: Us[] = [];
  selectedUsAdd: Us;

  constructor(
    private formBuilder: FormBuilder,
    private projectService: ProjectService,
    private releaseService: ReleaseService,
    private usService: UsService,
    private router: Router,
    private route: ActivatedRoute,
    private modalService: NgbModal) {
        this.releaseForm = this.formBuilder.group({
          versionMajor: '1',
          versionMinor: '0',
          versionPatch: '0',
          title: '',
          description: '',
          link: '',
      });
    }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.projectService.getProject(parseInt(params.get('projectId'), 10)).subscribe(
        project => {
          this.projectService.setCurrentProject(Project.fromJSON(project));
          this.usService.getUnreleasedForProject(this.projectService.currentProject.getId());
      });
    });
  }

  open(content): void {
    this.updatePossibleUserStories();
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
        if (result === 'Save click') {
          console.log(this.releaseForm.value);
          const release = new Release(
            this.projectService.currentProject.getId(),
            new ReleaseVersion(
              parseInt(this.releaseForm.value.versionMajor, 10),
              parseInt(this.releaseForm.value.versionMinor, 10),
              parseInt(this.releaseForm.value.versionPatch, 10)
            ),
            this.releaseForm.value.title,
            this.releaseForm.value.description,
            this.releaseForm.value.link,
            null,
            this.toAddUserStories,
          );

          console.log(release);
          this.releaseService.post(
              this.projectService.currentProject.getId(),
              release
          ).subscribe(next => console.log('Release added !'));
        }
    });
  }

  updatePossibleUserStories(): void {
    this.unreleased = this.usService.getUnreleased(this.projectService.currentProject.getId());
    this.possibleUserStories = this.unreleased.filter(us => !this.toAddUserStories.includes(us) );
  }

  addUs(): void {
    if (!this.selectedUsAdd) { return; }
    this.toAddUserStories.push(this.selectedUsAdd);
    this.updatePossibleUserStories();
  }

  deleteUs(us: Us): void {
    this.toAddUserStories.splice(this.toAddUserStories.indexOf(us), 1);
    this.updatePossibleUserStories();
  }
}
