package me.liuningning.framework.skin.support;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.liuningning.framework.skin.SkinManager;

public enum SkinType {
    SRC("SRC") {
        @Override
        public void apply(View view, String name) {
            Drawable drawable = SkinManager.getInstance().getResourceManager().getDrawable(name);
            if (view instanceof ImageView) {
                ((ImageView) view).setImageDrawable(drawable);
            }
        }
    },
    BACKGROUND("BACKGROUND") {
        @Override
        public void apply(View view, String name) {
            int color = SkinManager.getInstance().getResourceManager().getColor(name);
            view.setBackgroundColor(color);
        }
    },

    TEXT_COLOR("TEXTCOLOR") {
        @Override
        public void apply(View view, String name) {
            int color = SkinManager.getInstance().getResourceManager().getColor(name);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            }

        }
    };

    private final String mName;

    SkinType(String name) {
        this.mName = name;
    }

    public abstract void apply(View view, String name);


    public static SkinType getSkinType(String name) {

        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (!TextUtils.isEmpty(name) && name.toUpperCase().equals(skinType.mName)) {
                return skinType;
            }
        }

        return null;
    }

}
