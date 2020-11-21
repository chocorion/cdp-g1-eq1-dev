import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Us} from '../models/us.model';
import {environment} from '../../environments/environment';
import {GenericService} from './genericService';

@Injectable({
    providedIn: 'root'
})
export class UsService extends GenericService<Us>{

    constructor(private http: HttpClient) {
        super(http, 'us');
    }

    getById(projectId: number, usId: number): Observable<Us> {
        return this.http.get<Us>(environment.apiUrl + `projects/${projectId}/us/${usId}`);
    }
}
