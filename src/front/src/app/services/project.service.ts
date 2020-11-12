import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {Project} from '../model/project.model';

@Injectable({
    providedIn: 'root'
})
export class ProjectService {

    constructor(private http: HttpClient) {
    }

    getProjects(): Observable<Project[]> {
        return this.http.get<Project[]>(environment.apiUrl + 'projects');
    }

    getProject(id: number): Observable<Project> {
        return this.http.get<Project>(environment.apiUrl + 'projects/' + id);
    }

    updateProject(project: Project): Observable<Project> {
        return this.http.put<Project>(environment.apiUrl + 'projects/' + project.getId(), project);
    }

    postProject(project: Project): Observable<Project> {
        return this.http.post<Project>(environment.apiUrl + 'projects/' + project.getId(), project);
    }

    deleteProject(project: Project): void {
        this.http.delete(environment.apiUrl + 'projects/' + project.getId());
    }
}
