export class Project{
    public id: number;
    private name: string;
    private description: string;

    constructor(id: number , name: string, description: string){
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
    }

    static fromJSON(json): Project {
        return new Project(
            json.id,
            json.name,
            json.description,
        );
    }

    static assign(project : Project): Project {
        return new Project(
            project.getId(),
            project.getName(),
            project.getDescription()
        );
    }

    public setName(name: string): void {
        this.name = name;
    }

    public setDescription(description: string): void {
        this.description = description;
    }

    public setId(id: number): void {
        if (id < 0) { id = 0; }
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
