package com.example.demospringboot.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrivilegePayload implements Serializable {

    @ApiModelProperty(value = "權限 ID")
    private Integer privilegeId;
    @ApiModelProperty(value = "權限名稱")
    private String privilegeName;

    public Integer getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Integer privilegeId) {
        this.privilegeId = privilegeId;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public static Builder init() {
        return new Builder();
    }

    public static class Builder {

        private PrivilegePayload payload;

        private Builder() {
            this.payload = new PrivilegePayload();
        }

        public Builder id(Integer privilegeId) {
            this.payload.privilegeId = privilegeId;
            return this;
        }

        public Builder name(String privilegeName) {
            this.payload.privilegeName = privilegeName;
            return this;
        }

        public PrivilegePayload build() {
            return this.payload;
        }
    }
}
