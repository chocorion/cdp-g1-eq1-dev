import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Test} from '../models/test.model';
import {GenericService} from './genericService';

@Injectable({
    providedIn: 'root'
})
export class TestService extends GenericService<Test>{
    constructor(private http: HttpClient) {
        super(http, 'tests');
    }
}
