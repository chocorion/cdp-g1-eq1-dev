import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Us} from '../models/us.model';
import {environment} from '../../environments/environment';
import {GenericService} from './genericService';
import {map} from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class UsService extends GenericService<Us> {

    protected Unreleased = {};

    constructor(private http: HttpClient) {
        super(http, 'us');
    }

    getById(projectId: number, usId: number): Observable<Us> {
        return this.http.get<Us>(environment.apiUrl + `projects/${projectId}/us/${usId}`);
    }

    getElementFromJSON(jsonObject): Us {
        return Us.fromJSON(jsonObject);
    }

    getUnreleasedForProject(projectId: number): void {
        const url = environment.apiUrl + `projects/${projectId}/us/unreleased`;

        this.http.get<Us[]>(url).subscribe(
            result => {
                this.Unreleased[projectId] = result.map(element => this.getElementFromJSON(element));
                this.emit(projectId);
            }
        );
    }

    getUnreleased(projectId: number): Us[] {
        return this.Unreleased[projectId];
    }
}
