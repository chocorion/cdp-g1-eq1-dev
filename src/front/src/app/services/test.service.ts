import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, Subject, throwError} from 'rxjs';
import {Test} from '../models/test.model';
import {environment} from '../../environments/environment';
import {catchError} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class TestService {
    constructor(private http: HttpClient) {
    }

    private static handleError(error: HttpErrorResponse): Observable<never> {
        if (error.error instanceof ErrorEvent) {
            // A client-side or network error occurred. Handle it accordingly.
            console.error('An error occurred:', error.error.message);
        } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong.
            console.error(
                `Backend returned code ${error.status}, ` +
                `body was: ${error.error}`);
        }
        // Return an observable with a user-facing error message.
        return throwError(
            'Something bad happened; please try again later.');
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
        this.http.delete(environment.apiUrl + `projects/${test.getProjectId()}/tests/${test.getId()}`).pipe(
            catchError(TestService.handleError)
        );
    }
}
