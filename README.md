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
Tag 为需要使用的版本，例如当前最新的版本为 v1.1 。


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
			//需要重新申请权限的时候，回调出那些权限是没有开启的，可以在这重新调用 requestPermissions 请求
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

#### 在页面结束 ondestroy release
```java
 PermissionHelper.getInstance().release();
```
#### 正常权限总结
```
ACCESS_LOCATION_EXTRA_COMMANDS
ACCESS_NETWORK_STATE
ACCESS_NOTIFICATION_POLICY
ACCESS_WIFI_STATE
BLUETOOTH
BLUETOOTH_ADMIN
BROADCAST_STICKY
CHANGE_NETWORK_STATE
CHANGE_WIFI_MULTICAST_STATE
CHANGE_WIFI_STATE
DISABLE_KEYGUARD
EXPAND_STATUS_BAR
GET_PACKAGE_SIZE
INSTALL_SHORTCUT
INTERNET
KILL_BACKGROUND_PROCESSES
MODIFY_AUDIO_SETTINGS
NFC
READ_SYNC_SETTINGS
READ_SYNC_STATS
RECEIVE_BOOT_COMPLETED
REORDER_TASKS
REQUEST_INSTALL_PACKAGES
SET_ALARM
SET_TIME_ZONE
SET_WALLPAPER
SET_WALLPAPER_HINTS
TRANSMIT_IR
UNINSTALL_SHORTCUT
USE_FINGERPRINT
VIBRATE
WAKE_LOCK
WRITE_SYNC_SETTINGS

```
#### 危险权限总结
```
group:android.permission-group.CONTACTS
  permission:android.permission.WRITE_CONTACTS
  permission:android.permission.GET_ACCOUNTS
  permission:android.permission.READ_CONTACTS

group:android.permission-group.PHONE
  permission:android.permission.READ_CALL_LOG
  permission:android.permission.READ_PHONE_STATE
  permission:android.permission.CALL_PHONE
  permission:android.permission.WRITE_CALL_LOG
  permission:android.permission.USE_SIP
  permission:android.permission.PROCESS_OUTGOING_CALLS
  permission:com.android.voicemail.permission.ADD_VOICEMAIL

group:android.permission-group.CALENDAR
  permission:android.permission.READ_CALENDAR
  permission:android.permission.WRITE_CALENDAR

group:android.permission-group.CAMERA
  permission:android.permission.CAMERA

group:android.permission-group.SENSORS
  permission:android.permission.BODY_SENSORS

group:android.permission-group.LOCATION
  permission:android.permission.ACCESS_FINE_LOCATION
  permission:android.permission.ACCESS_COARSE_LOCATION

group:android.permission-group.STORAGE
  permission:android.permission.READ_EXTERNAL_STORAGE
  permission:android.permission.WRITE_EXTERNAL_STORAGE

group:android.permission-group.MICROPHONE
  permission:android.permission.RECORD_AUDIO

group:android.permission-group.SMS
  permission:android.permission.READ_SMS
  permission:android.permission.RECEIVE_WAP_PUSH
  permission:android.permission.RECEIVE_MMS
  permission:android.permission.RECEIVE_SMS
  permission:android.permission.SEND_SMS
  permission:android.permission.READ_CELL_BROADCASTS
```
