export class Project {
    public id: number;
    private name: string;
    private description: string;

    constructor( name: string, description: string, id?: number){
        this.setId((id) ? id : -1);
        this.setName(name);
        this.setDescription(description);
    }

    static fromJSON(json): Project {
        return new Project(
            json.name,
            json.description,
            json.id
        );
    }

    static assign(project: Project): Project {
        return new Project(
            project.getName(),
            project.getDescription(),
            project.getId(),
        );
    }

    public setName(name: string): void {
        this.name = name;
    }

    public setDescription(description: string): void {
        this.description = description;
    }

    public setId(id: number): void {
        if (id < 0) { id = null; }
        this.id = id;
    }

    public getId(): number{
        return this.id;
    }

    public getName(): string{
        return this.name;
    }

    public getDescription(): string{
        return this.description;
    }
}
