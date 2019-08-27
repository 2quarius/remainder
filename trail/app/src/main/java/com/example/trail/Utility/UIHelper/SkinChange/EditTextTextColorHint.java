package com.example.trail.Utility.UIHelper.SkinChange;

import android.content.res.ColorStateList;
import android.view.View;

import com.example.trail.Utility.UIHelper.CustomTextInputLayout;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.utils.SkinResourcesUtils;

public class EditTextTextColorHint extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if (view instanceof CustomTextInputLayout){
            CustomTextInputLayout layout = (CustomTextInputLayout) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
                ColorStateList color = SkinResourcesUtils.getColorStateList(attrValueRefId);
                layout.setHintTextColor(color);
            }
        }
    }
}
