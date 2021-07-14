export interface LoginResponse {
    jwt: string;
    refreshToken: string;
    expiresAt: Date;
    username: string;
}