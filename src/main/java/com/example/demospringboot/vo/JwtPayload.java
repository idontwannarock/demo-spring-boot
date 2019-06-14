package com.example.demospringboot.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtPayload implements Serializable {

    private static final long serialVersionUID = -5936438877375472216L;

    @ApiModelProperty(value = "JWT")
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public static Builder init() {
        return new Builder();
    }

    public static class Builder {

        private JwtPayload payload;

        private Builder() {
            this.payload = new JwtPayload();
        }

        public Builder jwt(String jwt) {
            this.payload.jwt = jwt;
            return this;
        }

        public JwtPayload build() {
            return this.payload;
        }
    }
}
