export class Project{
    private id: number;
    private name: string;
    private description: string;

    private readonly NAMESIZE: number = 50;
    private readonly DESCRIPTIONSIZE: number = 500;

    constructor(id: number , name: string, description: string){
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
    }

    public setName(name: string): void {
        if (name.length > this.NAMESIZE) {
            this.name = name.slice(0, this.NAMESIZE);
        }
        else {
            this.name = name;
        }
    }

    public setDescription(description: string): void {
        if (description.length > this.DESCRIPTIONSIZE) {
            this.description = description.slice(0, this.DESCRIPTIONSIZE);
        }
        else {
            this.description = description;
        }
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
