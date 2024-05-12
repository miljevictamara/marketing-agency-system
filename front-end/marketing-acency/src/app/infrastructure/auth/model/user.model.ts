export interface User {
  id?: number;
  mail: string;
  password: string;
  confirmationPassword?: string;
  isBlocked: boolean;
  isActivated: boolean;
}

