import { Role } from "./role.model";

export interface User {
  id?: number;
  mail: string;
  password: string;
  confirmationPassword: string;
  roles: Role[];
  isBlocked: boolean;
  isActivated: boolean;
}

