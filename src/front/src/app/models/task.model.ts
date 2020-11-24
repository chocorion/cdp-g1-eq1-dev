import {PossesId} from './possesId';

export class Task implements PossesId {
    private id: number;
    private projectId: number;
    private usId: number;
    private title: string;
    private duration: string;
    public status: string;

    constructor(id: number, projectId: number, usId: number, title: string, duration: string, status: string) {
        this.id = (id) ? id : -1;
        this.projectId = projectId;
        this.usId = usId;
        this.title = title;
        this.duration = duration;
        this.status = status;
    }

    static fromJSON(json): Task {
        return new Task(
            json.id,
            json.projectId,
            json.usId,
            json.title,
            json.duration,
            json.status
            );
    }

    getId(): number {
        return this.id;
    }

    setId(value: number): void {
        this.id = value;
    }

    getProjectId(): number {
        return this.projectId;
    }

    setProjectId(value: number): void {
        this.projectId = value;
    }

    getUsId(): number {
        return this.usId;
    }

    setUsId(value: number): void {
        this.usId = value;
    }

    getTitle(): string {
        return this.title;
    }

    setTitle(value: string): void {
        this.title = value;
    }

    getDuration(): string {
        return this.duration;
    }

    setDuration(value: string): void {
        this.duration = value;
    }

    getStatus(): string {
        return this.status;
    }

    setStatus(value: string): void {
        this.status = value;
    }
}
