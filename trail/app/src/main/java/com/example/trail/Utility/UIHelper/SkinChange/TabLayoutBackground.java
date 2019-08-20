package com.example.trail.Utility.UIHelper.SkinChange;

import android.view.View;

import com.google.android.material.tabs.TabLayout;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.utils.SkinResourcesUtils;

public class TabLayoutBackground extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if (view instanceof TabLayout){
            TabLayout tabLayout = (TabLayout) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
                int color = SkinResourcesUtils.getColor(attrValueRefId);
                tabLayout.setBackgroundColor(color);
            }
        }
    }
}
