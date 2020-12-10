import { Version } from '@angular/core';
import {PossesId} from './possesId';
import { Us } from './us.model';

export class Release implements PossesId {
    private projectId: number;
    private id: number;
    private version: Version;
    private title: string;
    private description: string;
    private link: string;
    private creationDate: string;
    private userStories: Us[];

    constructor(projectId: number, version: Version, title: string, description: string, link: string,
                creationDate: string, userStories: Us[], id?: number) {
        this.projectId = projectId;
        this.id = id;
        this.version = version;
        this.title = title;
        this.description = description;
        this.link = link;
        this.creationDate = creationDate;
        this.userStories = userStories;
    }

    static fromJSON(json): Release {
        return new Release(json.project, json.version, json.title, json.description, json.link,
        json.creationDate, json.userStories, json.id);
    }

    getProjectId(): number {
        return this.projectId;
    }

    getId(): number {
        return this.id;
    }

    setId(value: number): void {
        this.id = value;
    }

    getVersion(): Version {
        return this.version;
    }

    setVersion(value: Version): void {
        this.version = value;
    }

    getTitle(): string {
        return this.title;
    }

    setTitle(value: string): void {
        this.title = value;
    }

    getDescription(): string {
        return this.description;
    }

    setDescription(value: string): void {
        this.description = value;
    }

    getLink(): string {
        return this.link;
    }

    setLink(value: string): void {
        this.link = value;
    }

    getCreationDate(): string {
        return this.creationDate;
    }

    setCreationDate(value: string): void {
        this.creationDate = value;
    }

    getUserStories(): Us[] {
        return this.userStories;
    }

    setUserStories(value: Us[]): void {
        this.userStories = value;
    }


}
