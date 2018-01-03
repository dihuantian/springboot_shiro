package com.example.springboot_shiro.enums;

public enum ShiroTimeoutEnum {

    SHRIO_TIMEOUT(18000000),REDIS_TIMEOUT(18000),SHIRO_EXAMINE_TIME(1800000);

    private ShiroTimeoutEnum(int time) {
        this.time = time;
    }

    private int time;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
