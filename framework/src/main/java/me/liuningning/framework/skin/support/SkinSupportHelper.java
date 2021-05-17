package me.liuningning.framework.skin.support;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


public class SkinSupportHelper {


    public static SkinView parseSkinView(Context context, View view, AttributeSet attrs) {

        int attributeCount = attrs.getAttributeCount();

        SkinView skinView = null;

        for (int i = 0; i < attributeCount; i++) {
            String name = attrs.getAttributeName(i);
            SkinType skinType = SkinType.getSkinType(name);
            if (skinType == null) {
                continue;
            }
            if (skinView == null) {
                skinView = new SkinView(view);
            }

            String value = attrs.getAttributeValue(i);

            value = getResourceName(context, value);

            if (!TextUtils.isEmpty(value)) {
                SkinAttribute skinAttribute = new SkinAttribute(skinType, value);
                skinView.getSkinAttribute().add(skinAttribute);
            }


        }
        return skinView;

    }

    private static String getResourceName(Context context, String value) {
        if (value.startsWith("@")) {
            value = value.substring(1);
            int resId = Integer.parseInt(value);
            return context.getResources().getResourceEntryName(resId);
        } else if (value.startsWith("#")) {
            return value;
        }
        return null;
    }


}
