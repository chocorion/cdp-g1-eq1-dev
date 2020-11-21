import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Sprint} from '../models/sprint.model';
import {environment} from '../../environments/environment';
import {GenericService} from './genericService';

@Injectable({
    providedIn: 'root'
})
export class SprintService extends GenericService<Sprint> {

    constructor(private http: HttpClient) {
        super(http, 'sprints');
    }
}
