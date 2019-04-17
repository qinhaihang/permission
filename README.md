## 轻量级Android运行时权限请求库

#### 引入方式
1. 在项目的root build.gradle 中添加：
Add it in your root build.gradle at the end of repositories:

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

2. 在引入的 module 中添加依赖
```gradle
	dependencies {
	        implementation 'com.github.qinhaihang:permission:Tag'
	}
```
Tag 为需要使用的版本，当前最新的版本为 v1.0 。


#### 基本API方法介绍
1、 初始化工具 ``` init(FragmentActivity activity) ```
```java
    public PermissionHelper init(FragmentActivity activity) {
        mActivity = activity;
        return Holder.INSTANCE;
    }
```
2、 setmDenyPermissionCallback 
设置拒绝权限的回调接口

3、 setmRequestCallback
设置申请回调

4、 startSettingActivity 
打开设置界面

5、 
```java
/**
     * 查询权限是否开启,如果有提醒禁止再次提醒，则跳转到设置页面
     *
     * @param permissions
     */
    public void checkPermission(String... permissions);
```

6、
```java
/**
     * 请求权限
     * @param permissions
     */
    public void requestPermissions(String...permissions);
```

#### 调用示例
```java
 PermissionHelper.getInstance()
                .init(this)
                .setmDenyPermissionCallback(new ICallbackManager.IDenyPermissionCallback() {
                    @Override
                    public void onDenyPermissions(List<String> permissions) {
			//回调出那些权限是没有开启的，可以在这重新调用 requestPermissions 请求
                    }
                })
                .setmRequestCallback(new ICallbackManager.IRequestCallback() {
                    @Override
                    public void onAllPermissonGranted(boolean flag) {
			//请求的权限是否都已经授权
                    }
                })
                .checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
```



