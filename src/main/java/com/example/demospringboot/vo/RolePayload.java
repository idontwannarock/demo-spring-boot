package com.example.demospringboot.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolePayload implements Serializable {

    private static final long serialVersionUID = -7407878420548802919L;

    @ApiModelProperty(value = "角色 ID")
    private Integer roleId;
    @ApiModelProperty(value = "角色名稱")
    private String roleName;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public static Builder init() {
        return new Builder();
    }

    public static class Builder {

        private RolePayload payload;

        private Builder() {
            this.payload = new RolePayload();
        }

        public Builder id(Integer roleId) {
            this.payload.roleId = roleId;
            return this;
        }

        public Builder name(String roleName) {
            this.payload.roleName = roleName;
            return this;
        }

        public RolePayload build() {
            return this.payload;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RolePayload{");
        sb.append("roleId=").append(roleId);
        sb.append(", roleName='").append(roleName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
