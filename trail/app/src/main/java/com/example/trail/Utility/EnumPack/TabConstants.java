package com.example.trail.Utility.EnumPack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TabConstants {
    LISTS(0,"lists"),TIME(1,"time"),SPACE(2,"space"),SETTING(3,"setting");
    private int index;
    private String title;

}
