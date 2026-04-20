export interface RegisterRequest {
    username: string;
    custName: string;
    email: string;
    phoneNo: string;
    password: string;
}

export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginResponse {
    custId: number;
    custName: string;
    token: string;
    message: string;
}
