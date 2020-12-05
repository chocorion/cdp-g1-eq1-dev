import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Sprint} from '../models/sprint.model';
import {environment} from '../../environments/environment';
import {GenericService} from './genericService';
import {Us} from '../models/us.model';
import {ProjectService} from './project.service';
import {catchError} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class SprintService extends GenericService<Sprint> {

    constructor(
        private http: HttpClient,
        private projectService: ProjectService
    ) {
        super(http, 'sprints');
    }

    getUs(projectId: number, sprintId: number): Observable<Us[]> {
        return this.http.get<Us[]>(environment.apiUrl + `projects/${projectId}/sprints/${sprintId}/us`);
    }

    getElementFromJSON(jsonObject): Sprint {
        return Sprint.fromJSON(jsonObject);
    }

    getActifSprint(): Observable<Sprint> {
        return new Observable<Sprint>(observer => {
            this.http.get<Sprint>(
                environment.apiUrl + `projects/${this.projectService.currentProject.getId()}/sprints/active`
            ).subscribe(sprint => {
                observer.next(Sprint.fromJSON(sprint));
            }, error => {
                observer.next(null);
            });
        });
    }
}
