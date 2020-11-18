import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable, Subject} from 'rxjs';
import {Project} from '../models/project.model';

@Injectable({
    providedIn: 'root'
})
export class ProjectService {
    currentProject: Project = null;
    currentProjectSubject = new Subject<Project>();


    constructor(private http: HttpClient) {
    }

    setCurrentProject(project: Project): void {
        this.currentProject = project;
        this.emitCurrentProject();
    }

    clearCurrentProject(): void {
        this.currentProject = null;
        // emit null ?
    }

    emitCurrentProject(): void {
        this.currentProjectSubject.next(this.currentProject);
    }

    getProjects(): Observable<Project[]> {
        return this.http.get<Project[]>(environment.apiUrl + 'projects');
    }

    getProject(id: number): Observable<Project> {
        return this.http.get<Project>(environment.apiUrl + 'projects/' + id);
    }

    updateProject(project: Project): Observable<Project> {
        console.log("Up + " + project.getId() + ' ' + project.getName() + ' ' + project.getDescription());
        return this.http.put<Project>(environment.apiUrl + 'projects/', project);
    }

    postProject(project: Project): Observable<Project> {
        return this.http.post<Project>(environment.apiUrl + 'projects/' + project.getId(), project);
    }

    deleteProject(project: Project): void {
        this.http.delete(environment.apiUrl + 'projects/' + project.getId());
    }
}
