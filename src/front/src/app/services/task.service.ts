import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Task } from '../models/task.model';
import {environment} from '../../environments/environment';
import {GenericService} from './genericService';
import { DOD } from '../models/dod.model';

@Injectable({
    providedIn: 'root'
})
export class TaskService extends GenericService<Task>{

    constructor(private http: HttpClient) {
        super(http, 'tasks');
    }

    getAllForUs(projectId: number, usId: number): Observable<Task[]> {
        return this.http.get<Task[]>(environment.apiUrl + `projects/${projectId}/us/${usId}/tasks`);
    }

    getChildrenTasks(projectId: number, taskId: number): Observable<Task[]> {
        return this.http.get<Task[]>(environment.apiUrl + `projects/${projectId}/tasks/${taskId}/children`);
    }

    getParentTasks(projectId: number, taskId: number): Observable<Task[]> {
        return this.http.get<Task[]>(environment.apiUrl + `projects/${projectId}/tasks/${taskId}/parents`);
    }

    getDOD(projectId: number, taskId: number): Observable<DOD[]> {
        return this.http.get<DOD[]>(environment.apiUrl + `projects/${projectId}/tasks/${taskId}/DOD`);
    }

    updateDOD(projectId: number, taskId: number, dod: DOD): Observable<DOD> {
        return this.http.put<DOD>(environment.apiUrl + `projects/${projectId}/tasks/${taskId}/DOD`, dod);
    }

    getElementFromJSON(jsonObject): Task {
        return Task.fromJSON(jsonObject);
    }

}
