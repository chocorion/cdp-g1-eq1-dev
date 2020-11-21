import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Task } from '../models/task.model';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class TaskService {

    constructor(private http: HttpClient) {
    }

    getAllForProject(projectId: number): Observable<Task[]> {
        return this.http.get<Task[]>(environment.apiUrl + `projects/${projectId}/tasks`);
    }

    getAllForUs(projectId: number, usId: number): Observable<Task[]> {
        return this.http.get<Task[]>(environment.apiUrl + `projects/${projectId}/us/${usId}/tasks`);
    }

    post(projectId: number, task: Task): Observable<Task> {
        return this.http.post<Task>(environment.apiUrl + `projects/${projectId}/tasks`, task);
    }

    update(projectId: number, task: Task): Observable<Task> {
        return this.http.post<Task>(environment.apiUrl + `projects/${projectId}/tasks/${task.getId()}`, task);
    }

    delete(projectId: number, task: Task): void {
        this.http.delete(environment.apiUrl + `projects/${projectId}/tasks/${task.getId()}`);
    }
}
