import {environment} from '../../environments/environment';
import {Observable, Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {PossesId} from '../models/possesId';

export abstract class GenericService<T extends PossesId> {
    private static projectRoute = environment.apiUrl + 'projects/';

    protected TList: T[] = [];
    public subject = new Subject<T[]>();

    protected constructor(
        private httpClient: HttpClient,
        private routeBaseName: string
    ) {}

    emit(): void {
        this.subject.next(this.TList);
    }

    abstract getElementFromJSON(jsonObject): T;

    getAllForProject(projectId: number): void {
        const url = GenericService.projectRoute + `${projectId}/${this.routeBaseName}`;

        this.httpClient.get<T[]>(url).subscribe(
            result => {
                this.TList = result.map(element => this.getElementFromJSON(element));
                this.emit();
            }
        );
    }

    post(projectId: number, item: T): Observable<T> {
        const url = GenericService.projectRoute + `${projectId}/${this.routeBaseName}`;

        return new Observable<T>(
            subscriber => {
                this.httpClient.post<T>(url, item).subscribe(
                    result => {
                        console.log('Receive ' + JSON.stringify(result, null, 4));
                        result = this.getElementFromJSON(result);
                        console.log('Posting something... Id id : ' + result.getId());

                        this.TList.push(result);
                        subscriber.next(result);
                        this.emit();
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
                        result = this.getElementFromJSON(result);
                        const index = this.TList.findIndex(element => element.getId() === item.getId());
                        this.TList[index] = result;
                        subscriber.next(result);
                        this.emit();
                    }
                );
            }
        );
    }

    delete(projectId: number, item: T): void {
        const url = GenericService.projectRoute + `${projectId}/${this.routeBaseName}/${item.getId()}`;
        this.httpClient.delete(url).subscribe(
            result => {}
        );
    }
}
