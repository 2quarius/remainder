package com.example.trail.Utility.UIHelper.SkinChange;

import android.view.View;

import androidx.cardview.widget.CardView;

import solid.ren.skinlibrary.attr.base.SkinAttr;
import solid.ren.skinlibrary.utils.SkinResourcesUtils;

public class CardBackgroundColor extends SkinAttr {
    @Override
    protected void applySkin(View view) {
        if (view instanceof CardView){
            CardView cardView = (CardView) view;
            if (RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
                int color = SkinResourcesUtils.getColor(attrValueRefId);
                cardView.setCardBackgroundColor(color);
            }
        }
    }
}
