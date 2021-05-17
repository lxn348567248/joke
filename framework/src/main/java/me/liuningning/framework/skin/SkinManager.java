package me.liuningning.framework.skin;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import me.liuningning.framework.skin.support.ResourceManager;
import me.liuningning.framework.skin.support.SkinView;

public class SkinManager {

    private static SkinManager mInstance = new SkinManager();

    private ResourceManager mResourceManager;


    private Map<ISkinListener, List<SkinView>> mSkinView = new HashMap<>();


    private Context mContext;

    public static SkinManager getInstance() {
        return mInstance;
    }


    public ResourceManager getResourceManager() {
        return mResourceManager;
    }

    public void init(Context context) {
        this.mContext = context;
    }

    public void loadSkin(String path) {
        mResourceManager = new ResourceManager();
        mResourceManager.init(mContext);
        mResourceManager.load(path);

        notifySkinChange();
    }

    private void notifySkinChange() {
        for (Map.Entry<ISkinListener, List<SkinView>> entry : mSkinView.entrySet()) {
            List<SkinView> skinViews = entry.getValue();
            for (SkinView skinView : skinViews) {
                skinView.applySkin();
            }

            entry.getKey().onSkinChange();

        }
    }

    public void register(ISkinListener skinListener, SkinView skinView) {

        if (skinView == null) {
            return;
        }
        List<SkinView> skinViewList = mSkinView.get(skinListener);

        if (skinViewList == null) {
            skinViewList = new ArrayList<>();
            mSkinView.put(skinListener, skinViewList);
        }

        skinViewList.add(skinView);


    }

    public void unRegister(ISkinListener skinListener) {
        mSkinView.remove(skinListener);
    }

}
