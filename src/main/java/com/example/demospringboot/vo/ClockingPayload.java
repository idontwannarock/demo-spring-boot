package com.example.demospringboot.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClockingPayload implements Serializable {

    private static final long serialVersionUID = 2393355581165119340L;
    @ApiModelProperty(value = "打卡記錄編號")
    private Long id;
    @ApiModelProperty(value = "打卡員工編號")
    private Long userId;
    @ApiModelProperty(value = "上班打卡時間")
    private Date clockInTime;
    @ApiModelProperty(value = "下班打卡時間")
    private Date clockOutTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(Date clockInTime) {
        this.clockInTime = clockInTime;
    }

    public Date getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(Date clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public static Builder init() {
        return new Builder();
    }

    public static class Builder {

        private ClockingPayload payload;

        private Builder() {
            this.payload = new ClockingPayload();
        }

        public Builder id(Long id) {
            this.payload.id = id;
            return this;
        }

        public Builder userId(Long userId) {
            this.payload.userId = userId;
            return this;
        }

        public Builder clockInAt(Date clockInTime) {
            this.payload.clockInTime = clockInTime;
            return this;
        }

        public Builder clockOutAt(Date clockOutTime) {
            this.payload.clockOutTime = clockOutTime;
            return this;
        }

        public ClockingPayload build() {
            return this.payload;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ClockingPayload{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", clockInTime=").append(clockInTime);
        sb.append(", clockOutTime=").append(clockOutTime);
        sb.append('}');
        return sb.toString();
    }
}
