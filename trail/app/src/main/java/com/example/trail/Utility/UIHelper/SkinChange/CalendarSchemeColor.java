package com.example.trail.Utility.UIHelper.SkinChange;

import android.view.View;

import com.haibin.calendarview.CalendarView;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.utils.SkinResourcesUtils;

public class CalendarSchemeColor extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if (view instanceof CalendarView){
            CalendarView calendarView = (CalendarView) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
                int color = SkinResourcesUtils.getColor(attrValueRefId);
                calendarView.setSchemeColor(color,color,color);
            }
        }
    }
}
