import {PossesId} from './possesId';

export class DOD implements PossesId {
    private id: number;
    private projectId: number;
    private taskId: number;
    private description: string;
    public state: boolean;


    constructor(id: number, projectId: number, taskId: number, description: string, state: boolean) {
        this.id = (id) ? id : -1;
        this.projectId = projectId;
        this.taskId = taskId;
        this.description = description;
        this.state = state;
    }

    static fromJSON(json): DOD {
        return new DOD(
            json.id,
            json.projectId,
            json.taskId,
            json.description,
            json.state
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

    getTaskId(): number {
        return this.taskId;
    }

    setTaskId(value: number): void {
        this.taskId = value;
    }

    getDescription(): string {
        return this.description;
    }

    setDescription(value: string): void {
        this.description = value;
    }

    getState(): boolean {
        return this.state;
    }

    setState(value: boolean): void{
        this.state = value;
    }


}
