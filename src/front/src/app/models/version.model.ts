export class Version {
    private versionMajor: number;
    private versionMinor: number;
    private versionPatch: number;

    constructor(versionMajor: number, versionMinor: number, versionPatch: number){
        this.versionMajor = versionMajor;
        this.versionMinor = versionMinor;
        this.versionPatch = versionPatch;
    }

    static fromJSON(json): Version {
        return new Version(json.versionMajor, json.versionMinor, json.versionPatch);
    }

    getVersionMajor(): number {
        return this.versionMajor;
    }

    getVersionMinor(): number {
        return this.versionMinor;
    }

    getVersionPatch(): number {
        return this.versionPatch;
    }

    setVersionMajor(value: number): void {
        this.versionMajor = value;
    }

    setVersionMinor(value: number): void {
        this.versionMinor = value;
    }

    setVersionPatch(value: number): void {
        this.versionPatch = value;
    }
}
