package com.cg.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "bus_route")
public class BusRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String src;
    private String dest;

    public BusRoute() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSrc() { return src; }
    public void setSrc(String src) { this.src = src; }
    public String getDest() { return dest; }
    public void setDest(String dest) { this.dest = dest; }
}