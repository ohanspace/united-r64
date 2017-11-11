package org.badhan.r64.service.auth;

import org.badhan.r64.core.ServiceResponse;

public abstract class LoginResponse extends ServiceResponse {
    public int id;
    public String username;
    public String avatarUrl;
    public String displayName;
    public String email;
    public boolean hasPassword;
    public String authToken;
}
