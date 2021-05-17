package me.liuningning.framework.skin.support;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Method;

public class ResourceManager {
    private Context mContext;

    private String mPackageName;

    private Resources mResource;

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void load(String skinPath) {
        try {

            mPackageName = mContext.getPackageManager().getPackageArchiveInfo(
                    skinPath, PackageManager.GET_ACTIVITIES).packageName;
            Resources originResource = mContext.getResources();
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPathMethod = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, skinPath);
            mResource = new Resources(assetManager, originResource.getDisplayMetrics(), originResource.getConfiguration());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public Drawable getDrawable(String name) {
        int drawableId = mResource.getIdentifier(name, "drawable", mPackageName);
        return mResource.getDrawable(drawableId);
    }

    public int getColor(String name) {
        if (name.startsWith("#")) {
            return Color.parseColor(name);
        }
        int colorId = mResource.getIdentifier(name, "color", mPackageName);
        return mResource.getColor(colorId);
    }
}
