import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Sprint} from '../models/sprint.model';
import {environment} from '../../environments/environment';
import {GenericService} from './genericService';
import {Us} from '../models/us.model';

@Injectable({
    providedIn: 'root'
})
export class SprintService extends GenericService<Sprint> {

    constructor(private http: HttpClient) {
        super(http, 'sprints');
    }

    getUs(projectId: number, sprintId: number): Observable<Us[]> {
        return this.http.get<Us[]>(environment.apiUrl + `projects/${projectId}/sprints/${sprintId}/us`);
    }
}
