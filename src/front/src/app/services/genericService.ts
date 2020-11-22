import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {PossesId} from '../models/possesId';

export class GenericService<T extends PossesId> {
    private projectRoute = environment.apiUrl + 'projects/';

    constructor(
        private httpClient: HttpClient,
        private routeBaseName: string
    ) {}

    getAllForProject(projectId: number): Observable<T[]> {
        const url = this.projectRoute + `${projectId}/${this.routeBaseName}`;
        console.log('Get all projects for route ' + url);
        return this.httpClient.get<T[]>(url);
    }

    post(projectId: number, item: T): Observable<T> {
        return this.httpClient.post<T>(this.projectRoute + `${projectId}/${this.routeBaseName}`, item);
    }

    update(projectId: number, item: T): Observable<T> {
        return this.httpClient.put<T>(this.projectRoute + `${projectId}/${this.routeBaseName}/${item.getId()}`, item);
    }

    delete(projectId: number, item: T): void {
        this.httpClient.delete(this.projectRoute + `${projectId}/${this.routeBaseName}/${item.getId()}`);
    }
}
