package com.crazy.rain.usercenter.enums;

import lombok.Getter;

@Getter
public enum TeamStatusEnum {
    /**
     * 队伍状态枚举
     */
    PUBLIC(0, "公开"),
    PRIVATE(1, "私有"),
    ENCRYPTION(2, "加密");

    private Integer val;
    private String key;

    TeamStatusEnum(Integer val, String key) {
        this.val = val;
        this.key = key;
    }


    /**
     * 根据val 获取对应的枚举对象
     * @param val 枚举值
     * @return 枚举对象
     */
    public static TeamStatusEnum getTeamStatus(Integer val) {
        if (val == null) {
            return null;
        }
        TeamStatusEnum[] values = TeamStatusEnum.values();
        for (TeamStatusEnum statusEnum :
                values) {
            if (statusEnum.val.equals(val)) {
                return statusEnum;
            }
        }
        return null;
    }
}
