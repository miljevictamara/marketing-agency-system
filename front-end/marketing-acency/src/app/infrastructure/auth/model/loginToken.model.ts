import { User } from "./user.model";

export interface LoginToken {
    id: string;
    user: User;
    creationDate: Date;
    duration: number;
}