package com.example.trail.Utility.EnumPack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TabConstants {
    LISTS(0,"列表"),TIME(1,"日历"),SPACE(2,"地图"),SETTING(3,"设置");
    private int index;
    private String title;

}
