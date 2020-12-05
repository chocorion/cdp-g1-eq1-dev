import {PossesId} from './possesId';

export class Sprint implements PossesId {
    private projectId: number;
    private id: number;
    private name: string;
    private state: string;

    constructor(name: string, state: string, projectId: number, id?: number) {
        this.name = name;
        this.state = state;
        this.projectId = projectId;
        this.id = id;
    }

    static fromJSON(json): Sprint {
        return new Sprint(json.name, json.state, json.projectId, json.id);
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

    getName(): string {
        return this.name;
    }

    setName(value: string): void {
        this.name = value;
    }

    getState(): string {
        return this.state;
    }

    setState(value: string): void {
        this.state = value;
    }
}
