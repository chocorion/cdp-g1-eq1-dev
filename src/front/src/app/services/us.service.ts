import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Us} from '../models/us.model';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class UsService {

    constructor(private http: HttpClient) {
    }

    getAllForProject(projectId: number): Observable<Us[]> {
        return this.http.get<Us[]>(environment.apiUrl + `projects/${projectId}/us`);
    }

    getById(projectId: number, usId: number): Observable<Us> {
        return this.http.get<Us>(environment.apiUrl + `projects/${projectId}/us/${usId}`);
    }

    post(us: Us): void {
        this.http.post(environment.apiUrl + `projects/${us.getProjectId()}/us`, us);
    }

    delete(us: Us): void {
        this.http.delete(environment.apiUrl + `projects/${us.getProjectId()}/us/${us.getId()}`);
    }

    update(us: Us): Observable<Us> {
        return this.http.put<Us>(environment.apiUrl + `projects/${us.getProjectId()}/us/${us.getId()}`, us);
    }
}
