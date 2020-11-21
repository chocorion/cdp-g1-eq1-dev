export class Us{
    public id: number;
    private projectId: number;
    private description: string;
    private priority: string;
    private difficulty: number;

    constructor(id: number , projectId: number, priority: string, description: string, difficulty: number){
        this.setId(id);
        this.setProjectId(projectId);
        this.setDescription(description);
        this.setPriority(priority);
        this.setDifficulty(difficulty);
    }

    static fromJSON(json): Us {
        return new Us(
            json.id,
            json.projectId,
            json.description,
            json.priority,
            json.difficulty
        );
    }

    public setId(id: number): void {
        if (id < 0) { id = 0; }
        this.id = id;
    }

    public setProjectId(projectId: number): void {
        if (projectId < 0) { projectId = 0; }
        this.projectId = projectId;
    }

    public setDescription(description: string): void {
        this.description = description;
    }

    public setPriority(priority: string): void {
        this.priority = priority;
    }

    public setDifficulty(difficulty: number): void {
        if (difficulty < 0) { difficulty = 0; }
        this.difficulty = difficulty;
    }

    public getId(): number{
        return this.id;
    }

    public getProjectId(): number{
        return this.projectId;
    }

    public getDescription(): string{
        return this.description;
    }

    public getPriority(): string{
        return this.priority;
    }

    public getDifficulty(): number{
        return this.difficulty;
    }
}
