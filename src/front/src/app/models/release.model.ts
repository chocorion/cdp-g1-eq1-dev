import { ReleaseVersion } from './releaseversion.model';
import {PossesId} from './possesId';
import { Us } from './us.model';

export class Release implements PossesId {
    private project: number;
    private id: number;
    private version: ReleaseVersion;
    private title: string;
    private description: string;
    private link: string;
    private creationDate: string;
    private userStories: Us[];

    constructor(project: number, version: ReleaseVersion, title: string, description: string, link: string,
                creationDate: string, userStories: Us[], id?: number) {
        this.project = project;
        this.id = id;
        this.version = version;
        this.title = title;
        this.description = description;
        this.link = link;
        this.creationDate = !creationDate ? new Date().toISOString().slice(0, 10) : creationDate;
        this.userStories = userStories;
    }

    static fromJSON(json): Release {
        return new Release(json.project, json.version, json.title, json.description, json.link,
        json.creationDate, json.userStories, json.id);
    }

    getProjectId(): number {
        return this.project;
    }

    getId(): number {
        return this.id;
    }

    setId(value: number): void {
        this.id = value;
    }

    getVersion(): ReleaseVersion {
        return this.version;
    }

    setVersion(value: ReleaseVersion): void {
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
