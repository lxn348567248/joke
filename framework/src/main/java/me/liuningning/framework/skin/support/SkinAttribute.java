package me.liuningning.framework.skin.support;


import android.util.Log;
import android.view.View;

public class SkinAttribute {

    private SkinType mSkinType;
    private String value;

    public SkinAttribute(SkinType skinType, String value) {
        this.mSkinType = skinType;
        this.value = value;
    }


    public void apply(View view) {
        Log.d("TAG", "apply view:" + view);
        mSkinType.apply(view, value);
    }

    @Override
    public String toString() {
        return "SkinAttribute{" +
                "mSkinType=" + mSkinType +
                ", value='" + value + '\'' +
                '}';
    }
}
