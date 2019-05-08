package com.qhh.permission;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.qhh.permission.bean.Permission;
import com.qhh.permission.callback.ICallbackManager;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author qinhaihang_vendor
 * @version $Rev$
 * @time 2019/2/15 17:23
 * @des
 * @packgename com.sensetime.qinhaihang_vendor.qhhpermissionutils
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes
 */
public class PermissionHelper {

    private static final String REQUEST_PERMISSION = "request_permission";

    private ICallbackManager.IDenyPermissionCallback mDenyPermissionCallback;
    private ICallbackManager.IRequestCallback mRequestCallback;
    private WeakReference<FragmentActivity> mWeakActivityRef;

    private static class Holder{
        private static PermissionHelper INSTANCE = new PermissionHelper();
    }

    public PermissionHelper() {
    }

    public static PermissionHelper getInstance(){
        return Holder.INSTANCE;
    }

    public PermissionHelper init(FragmentActivity activity) {
        mWeakActivityRef = new WeakReference<>(activity);
        return Holder.INSTANCE;
    }

    private PermissionFragment getFragment() {
        FragmentManager manager = mWeakActivityRef.get().getSupportFragmentManager();
        PermissionFragment fragment = (PermissionFragment) manager.findFragmentByTag(REQUEST_PERMISSION);

        if (null == fragment) {
            fragment = PermissionFragment.getInstance();
            manager.beginTransaction()
                    .add(fragment, REQUEST_PERMISSION)
                    .commitAllowingStateLoss();
            manager.executePendingTransactions(); //立即执行 commit 的事务
        }

        return fragment;
    }

    public PermissionHelper setmDenyPermissionCallback(ICallbackManager.IDenyPermissionCallback mDenyPermissionCallback) {
        this.mDenyPermissionCallback = mDenyPermissionCallback;
        return Holder.INSTANCE;
    }

    public PermissionHelper setmRequestCallback(ICallbackManager.IRequestCallback mRequestCallback) {
        this.mRequestCallback = mRequestCallback;
        return Holder.INSTANCE;
    }

    /**
     * 打开设置页面打开权限
     *
     * @param context
     */
    public void startSettingActivity(@NonNull Activity context) {

        try {
            Intent intent =
                    new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" +
                            context.getPackageName()));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询权限是否开启,如果有提醒禁止再次提醒，则跳转到设置页面
     *
     * @param permissions
     */
    public void checkPermission(String... permissions) {

        getFragment().checkPermission(new ICallbackManager.IPermissionListCallback() {
            @Override
            public void onResultCallback(List<Permission> permissions) {
                boolean granted = true;
                if(null != permissions){
                    for (Permission permission : permissions) {
                        if (!permission.granted) {
                            granted = permission.granted;
                            break;
                        }
                    }
                }
                if (null != mRequestCallback) {
                    mRequestCallback.onAllPermissonGranted(granted);
                }
            }

            @Override
            public void onCheckResultCallback(List<String> permissions) {

                if (null != mDenyPermissionCallback) {
                    mDenyPermissionCallback.onDenyPermissions(permissions);
                }else{
                    requestPermissions(permissions.toArray(new String[permissions.size()]));
                }

            }
        }, permissions);
    }

    /**
     * 请求权限
     * @param permissions
     */
    public void requestPermissions(String...permissions){
        getFragment().requestPermissions(permissions, new ICallbackManager.IPermissionListCallback() {
            @Override
            public void onResultCallback(List<Permission> permissions) {
                boolean granted = true;
                if(null != permissions){
                    for (Permission permission : permissions) {
                        if (!permission.granted) {
                            granted = permission.granted;
                            break;
                        }
                    }
                }
                if (null != mRequestCallback) {
                    mRequestCallback.onAllPermissonGranted(granted);
                }
            }

            @Override
            public void onCheckResultCallback(List<String> permissions) {
                if (null != mDenyPermissionCallback) {
                    mDenyPermissionCallback.onDenyPermissions(permissions);
                }
            }
        });
    }

    /**
     * 释放 fragment
     */
    public void release(){

        if(mWeakActivityRef != null && mWeakActivityRef.get() != null){
            FragmentManager manager = mWeakActivityRef.get().getSupportFragmentManager();
            PermissionFragment fragment = (PermissionFragment) manager.findFragmentByTag(REQUEST_PERMISSION);

            if(!manager.isDestroyed() && fragment != null){
                manager.beginTransaction().remove(fragment).commitAllowingStateLoss();
                manager.executePendingTransactions();
            }

            mWeakActivityRef.clear();
        }

    }

}
