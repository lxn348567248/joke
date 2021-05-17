package me.liuningning.framework.skin.support;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.liuningning.framework.skin.ISkinListener;

public class SkinView implements ISkinListener {

    private View mView;

    private List<SkinAttribute> mSkinAttribute = new ArrayList<>();

    public SkinView(View view) {
        this.mView = view;
    }

    public List<SkinAttribute> getSkinAttribute() {
        return mSkinAttribute;
    }

    public void applySkin() {
        for (SkinAttribute skinAttribute : mSkinAttribute) {
            skinAttribute.apply(mView);
        }

    }

    @Override
    public String toString() {
        return "SkinView{" +
                "mView=" + mView +
                ", mSkinAttribute=" + mSkinAttribute +
                '}';
    }


    @Override
    public void onSkinChange() {

    }
}
