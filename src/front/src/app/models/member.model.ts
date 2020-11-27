export class Member {
    public user: number;
    private project: number;
    private name: string;
    private role: string;
    private level: string;

    constructor(project: number, name: string, role: string, level: string, user?: number) {
        this.setUser((user) ? user : -1);
        this.setProject(project);
        this.setName(name);
        this.setRole(role);
        this.setLevel(level);
    }

    static fromJSON(json): Member {
        return new Member(
            json.project,
            json.name,
            json.role,
            json.level,
            json.user,
        );
    }

    static assign(member: Member): Member {
        return new Member(
            member.getProject(),
            member.getName(),
            member.getRole(),
            member.getLevel(),
            member.getUser()
        );
    }

    public getUser(): number {
        return this.user;
    }

    public setUser(user: number): void {
        if (user < 0) { user = null; }
        this.user = user;
    }

    public getProject(): number {
        return this.project;
    }

    public setProject(project: number): void {
        this.project = project;
    }

    public getName(): string {
        return this.name;
    }

    public setName(name: string): void {
        this.name = name;
    }

    public getRole(): string {
        return this.role;
    }

    public setRole(role: string): void {
        this.role = role;
    }
    public getLevel(): string {
        return this.level;
    }

    public setLevel(level: string): void {
        this.level = level;
    }
}
