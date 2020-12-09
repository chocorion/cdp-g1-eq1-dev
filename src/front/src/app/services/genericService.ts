import {environment} from '../../environments/environment';
import {Observable, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {PossesId} from '../models/possesId';

export abstract class GenericService<T extends PossesId> {
    private static projectRoute = environment.apiUrl + 'projects/';

    protected TList = {};
    private subject = {};

    protected constructor(
        private httpClient: HttpClient,
        private routeBaseName: string
    ) {}

    emit(projectId): void {
        if (this.subject[projectId] !== undefined) {
            this.subject[projectId].next(this.TList[projectId]);
        }
    }

    abstract getElementFromJSON(jsonObject): T;

    getSubject(projectId: number): Subject<T[]> {
        if (this.subject[projectId] === undefined) {
            this.subject[projectId] = new Subject<T[]>();
            this.TList[projectId] = [];
        }

        return this.subject[projectId];
    }

    getAllForProject(projectId: number): void {
        const url = GenericService.projectRoute + `${projectId}/${this.routeBaseName}`;

        this.httpClient.get<T[]>(url).subscribe(
            result => {
                this.TList[projectId] = result.map(element => this.getElementFromJSON(element));
                this.emit(projectId);
            }
        );
    }

    post(projectId: number, item: T): Observable<T> {
        const url = GenericService.projectRoute + `${projectId}/${this.routeBaseName}`;

        return new Observable<T>(
            subscriber => {
                this.httpClient.post<T>(url, item).subscribe(
                    result => {
                        result = this.getElementFromJSON(result);

                        this.TList[projectId].push(result);
                        subscriber.next(result);
                        this.emit(projectId);
                    }
                );
            }
        );
    }

    update(projectId: number, item: T): Observable<T> {
        const url = GenericService.projectRoute + `${projectId}/${this.routeBaseName}/${item.getId()}`;

        return new Observable<T>(
            subscriber => {
                this.httpClient.put<T>(url, item).subscribe(
                    result => {
                        console.log('In update : ' + this.TList[projectId]);
                        result = this.getElementFromJSON(result);
                        const index = (this.TList[projectId] as T[]).findIndex(element => element.getId() === item.getId());
                        this.TList[projectId][index] = result;
                        subscriber.next(result);
                        this.emit(projectId);
                    }
                );
            }
        );
    }

    delete(projectId: number, item: T): void {
        const url = GenericService.projectRoute + `${projectId}/${this.routeBaseName}/${item.getId()}`;
        this.httpClient.delete(url).subscribe(
            result => {
                const index = (this.TList[projectId] as T[]).findIndex(element => element.getId() === item.getId());
                this.TList[projectId].splice(index, 1);
                this.emit(projectId);
            }
        );
    }
}
