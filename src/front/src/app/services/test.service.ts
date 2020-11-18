import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs';
import {Test} from '../models/test.model';
import {environment} from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class TestService {
    constructor(private http: HttpClient) {
    }

    getAllForProject(projectId: number): Observable<Test[]> {
        return this.http.get<Test[]>(environment.apiUrl + `projects/${projectId}/tests`);
    }

    postTest(test: Test): Observable<Test> {
        return this.http.post<Test>(environment.apiUrl + `projects/${test.getProjectId()}/tests`, test);
    }

    updateTest(test: Test): Observable<Test> {
        return this.http.put<Test>(environment.apiUrl + `projects/${test.getProjectId()}/tests/${test.getId()}`, test);
    }

    deleteTest(test: Test): void {
        this.http.delete(environment.apiUrl + `projects/${test.getProjectId()}/tests/${test.getId()}`);
    }
}
