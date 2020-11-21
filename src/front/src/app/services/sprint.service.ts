import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Sprint} from '../models/sprint.model';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class SprintService {

    constructor(private http: HttpClient) {
    }

    getAllForProject(projectId: number): Observable<Sprint[]> {
        return this.http.get<Sprint[]>(environment.apiUrl + `projects/${projectId}/sprints`);
    }

    postSprint(projectId: number, sprint: Sprint): Observable<Sprint> {
        return this.http.post<Sprint>(environment.apiUrl + `projects/${projectId}`, sprint);
    }

    updateSprint(projectId: number, sprint: Sprint): Observable<Sprint> {
        return this.http.put<Sprint>(environment.apiUrl + `projects/${projectId}/sprints/${sprint.getId()}`, sprint);
    }

    deleteSprint(projectId: number, sprint: Sprint): void {
        this.http.delete(environment.apiUrl + `projects/${projectId}/sprints/${sprint.getId()}`);
    }
}
