import { Component, OnInit, Version } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ReleaseVersion} from '../../../../models/releaseversion.model';
import {ProjectService} from '../../../../services/project.service';
import {ReleaseService} from '../../../../services/release.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import { Release } from 'src/app/models/release.model';

@Component({
  selector: 'app-release-create-form',
  templateUrl: './release-create-form.component.html',
  styleUrls: ['./release-create-form.component.css']
})
export class ReleaseCreateFormComponent implements OnInit {
  releaseForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private projectService: ProjectService,
    private releaseService: ReleaseService,
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
  }

  open(content): void {
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
              null,
            );

            console.log(release);
            this.releaseService.post(
                this.projectService.currentProject.getId(),
                release
            ).subscribe(next => console.log('Release added !'));
          }
      });
  }

}
