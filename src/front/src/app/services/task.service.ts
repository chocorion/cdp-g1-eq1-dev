import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import { Task } from '../models/task.model';
import {environment} from '../../environments/environment';
import {GenericService} from './genericService';

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

    getElementFromJSON(jsonObject): Task {
        return Task.fromJSON(jsonObject);
    }
}
