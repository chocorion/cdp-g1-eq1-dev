export class Member {
    public user : number;
    private projectId : number;
    private role : string;
    private level : string;

    constructor(projectId :number, role :string, level:string, user?: number ){
        this.setUser((user) ? user:-1);
        this.setProjectId(projectId);
        this.setRole(role);
        this.setLevel(level);
    }

    static fromJson(json) : Member {
        return new Member(
            json.user,
            json.projectId,
            json.role,
            json.level
        );
    }

    static assign(member : Member) : Member {
        return new Member(
            member.getProjectId(),
            member.getRole(),
            member.getLevel(),
            member.getUser()
        );
    }

    public getUser() : number {
        return this.user;
    }

    public setUser(user :number):void{
        if(user < 0) {user = null;}
        this.user = user;
    }

    public getProjectId() : number {
        return this.projectId;
    }

    public setProjectId(projectId :number):void{
        this.projectId =projectId;
    }
    public getRole() : string {
        return this.role;
    }

    public setRole(role :string):void{
        this.role =role;
    }
    public getLevel() : string {
        return this.level;
    }

    public setLevel(level :string):void{
        this.level =level;
    }
}