package com.olisa_td.gatewayservice.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("role")
    private String role;


    public TokenResponse(String id, String role) {
        this.id = id;
        this.role = role;
    }




    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "TokenResponse{" +
                "id='" + id + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
