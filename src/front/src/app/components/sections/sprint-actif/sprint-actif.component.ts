import { Component, OnInit } from '@angular/core';
import { Sprint } from 'src/app/models/sprint.model';
import { Us } from 'src/app/models/us.model';
import { ProjectService } from 'src/app/services/project.service';
import { SprintService } from 'src/app/services/sprint.service';

@Component({
  selector: 'app-sprint-actif',
  templateUrl: './sprint-actif.component.html',
  styleUrls: ['./sprint-actif.component.css']
})
export class SprintActifComponent implements OnInit {

  userStories: Us[] = [];
  sprint: Sprint;

  constructor(private sprintService: SprintService,
              private projectService: ProjectService) { }

  ngOnInit(): void {
    this.sprintService.getActiveSprint().subscribe(
      sprint => {
      this.sprint = sprint;
      this.sprintService.getUs(this.projectService.currentProject.getId(), this.sprint.getId()).subscribe(
            usList => {
                usList.forEach(us => this.userStories.push(Us.fromJSON(us)));
            }
        );
      });
  }

}
