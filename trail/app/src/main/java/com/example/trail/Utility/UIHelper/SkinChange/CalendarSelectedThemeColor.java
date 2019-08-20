package com.example.trail.Utility.UIHelper.SkinChange;

import android.view.View;

import com.example.trail.R;
import com.haibin.calendarview.CalendarView;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.utils.SkinResourcesUtils;

public class CalendarSelectedThemeColor extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if (view instanceof CalendarView){
            CalendarView calendarView = (CalendarView) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
                int color = SkinResourcesUtils.getColor(attrValueRefId);
                calendarView.setSelectedColor(color,SkinResourcesUtils.getColor(R.color.inputLine),SkinResourcesUtils.getColor(R.color.inputLine));
            }
        }
    }
}
