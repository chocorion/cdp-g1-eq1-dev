import {PossesId} from './possesId';

export class Sprint implements PossesId {
    private projectId: number;
    private id: number;
    private name: string;

    constructor(name: string, projectId: number, id?: number) {
        this.name = name;
        this.projectId = projectId;
        this.id = (id) ? id : -1;
    }

    static fromJSON(json): Sprint {
        return new Sprint(json.name, json.projectId, json.id);
    }

    getProjectId(): number {
        return this.projectId;
    }

    setProjectId(value: number): void {
        this.projectId = value;
    }

    getId(): number {
        return this.id;
    }

    setId(value: number): void {
        this.id = value;
    }

    getName(): string {
        return this.name;
    }

    setName(value: string): void {
        this.name = value;
    }
}
