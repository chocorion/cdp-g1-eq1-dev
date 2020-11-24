import {environment} from '../../environments/environment';
import {Subject} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {PossesId} from '../models/possesId';
import {FromJSON} from '../models/fromJSON';


export class GenericService<T extends PossesId & FromJSON<T>> {
    private static projectRoute = environment.apiUrl + 'projects/';

    private TList: T[];
    public subject = new Subject<T[]>();

    constructor(
        private httpClient: HttpClient,
        private routeBaseName: string
    ) {}

    emit(): void {
        this.subject.next(this.TList);
    }

    getAllForProject(projectId: number): void {
        const url = GenericService.projectRoute + `${projectId}/${this.routeBaseName}`;

        this.httpClient.get<T[]>(url).subscribe(
            result => {
                this.TList = result.map(element => element.fromJSON(element));
                this.emit();
            }
        );
    }

    post(projectId: number, item: T): void {
        const url = GenericService.projectRoute + `${projectId}/${this.routeBaseName}`;

        this.httpClient.post<T>(url, item).subscribe(
            result => {
                this.TList.push(result.fromJSON(result));
                this.emit();
            }
        );
    }

    update(projectId: number, item: T): void {
        const url = GenericService.projectRoute + `${projectId}/${this.routeBaseName}/${item.getId()}`;

        this.httpClient.put<T>(url, item).subscribe(
            result => {
                this.TList.push(result.fromJSON(result));
                this.emit();
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
