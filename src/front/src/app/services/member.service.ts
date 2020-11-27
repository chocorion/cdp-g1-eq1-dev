import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Member } from '../models/member.model';

@Injectable({
    providedIn: 'root'
})
export class MemberService {

    constructor(private http: HttpClient) {
    }

    getMembers(projectId: number): Observable<Member[]> {
        return this.http.get<Member[]>(environment.apiUrl + 'projects/' + projectId + '/members');
    }

    getMember(user: number, projectId: number): Observable<Member> {
        return this.http.get<Member>(environment.apiUrl + 'projects/' + projectId + '/members/' + user);
    }

    updateMember(member: Member): Observable<Member> {
        return this.http.put<Member>(environment.apiUrl + 'projects/' + member.getProject() + '/members', member);
    }

    postMember(member: Member): Observable<Member> {
        return this.http.post<Member>(environment.apiUrl + 'projects/' + member.getProject() + '/members', member);
    }

    deleteMember(member: Member): any {
        return this.http.delete(environment.apiUrl + 'projects/' + member.getProject() + '/members/' + member.getUser());
    }

}
