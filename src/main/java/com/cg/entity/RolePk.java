package com.cg.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RolePk implements Serializable {

    private String username;
    private String authority;

    public RolePk() {}

    public RolePk(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getAuthority() { return authority; }
    public void setAuthority(String authority) { this.authority = authority; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePk rolePk = (RolePk) o;
        return Objects.equals(username, rolePk.username) && Objects.equals(authority, rolePk.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authority);
    }
}
