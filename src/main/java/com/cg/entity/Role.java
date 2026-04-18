package com.cg.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "authorities")
public class Role {

    @EmbeddedId
    private RolePk id;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;

    public Role() {}

    public Role(RolePk id, User user) {
        this.id = id;
        this.user = user;
    }

    public RolePk getId() { return id; }
    public void setId(RolePk id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getAuthority() {
        return id != null ? id.getAuthority() : null;
    }
}
