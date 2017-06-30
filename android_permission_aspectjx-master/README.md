[aspectjx]:https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx

Android_Permission_AspectjX
------------
简单，方便的Android M动态权限配置框架，基于AOP方式[aspectjx]，支持类，方法的权限配置，更中意的是支持依赖库里的Activity的权限配置，你不再担心依赖第三方库出现权限问题怎么办了。

# 依赖

gradle 依赖

```
compile 'com.firefly1126.permissionaspect:permissionaspect:1.0.1'

```

[最新版本查看这里](http://jcenter.bintray.com/com/firefly1126/permissionaspect/permissionaspect/)

该库必须配合[aspectjx]使用

# 使用


#### 1. 在Application的onCreate里初始化

```
PermissionCheckSDK.init(Application);
```

#### 2. 在app层动态添加权限的两种方式

* 使用PermissionCheckSDK.addCheckPermissionItem(CheckPermissionItem item)

> 通过这种方式可以给app里的Activity及依赖的第三方库的Activity类分配动态权限

例如：

```
//在Application里初始化app里所有Activity的权限
 String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        AOPSDK.addCheckPermissionItem(new CheckPermissionItem("com.hujiang.hjwordbookuikit.app.activity.RawWordActivity", permissions));

```
* 使用注解的方式添加权限@NeedPermission

@NeedPermission可以作用于所有类的方法(包括静态方法和私有方法)及所有的Activity类

`注意：`

```
1、@NeedPermission建议不要使用在带返回值的函数上，因为函数一旦被中断，函数返回值可能会异常；
2、@NeedPermission建议不要使用在系统组件的生命周期回调接口上，因为这样可能会影响系统组件正常的生命周期（比如：Activity的onCreate，onResume，onPause等）。
```


```
//作用于Activity
@NeedPermission(permissions = {Manifest.permission.READ_CONTACTS
        , Manifest.permission.WRITE_CONTACTS})
public class BActivity extends Activity {
}

//作用于类的方法
    @NeedPermission(permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS})
    private void startBActivity(String name, long id) {
        startActivity(new Intent(MainActivity.this, BActivity.class));
    }

```

* @NeedPermission参数说明

```
    /**
     * 你所申请的权限列表，例如 {@link android.Manifest.permission#READ_CONTACTS}
     * @return 权限列表
     * @see android.Manifest.permission
     */
    String[] permissions() default "";

    /**
     * 合理性解释内容
     * @return 合理性解释内容
     */
    String rationalMessage() default "";

    /**
     * 合理性解释文本资源ID
     * @return
     */
    int rationalMsgResId() default 0;


    /**
     * 合理性解释按钮文本
     * @return 合理性解释按钮文本
     */
    String rationalButton() default "";

    /**
     * 合理性解释按钮文本资源ID
     * @return
     */
    int rationalBtnResId() default 0;

    /**
     * 权限禁止文本内容
     * @return 权限禁止文本内容
     */
    String deniedMessage() default "";

    /**
     * 权限禁止文本资源ID
     * @return
     */
    int deniedMsgResId() default 0;

    /**
     * 权限禁止按钮文本
     * @return 权限禁止按钮文本
     */
    String deniedButton() default "";

    /**
     * 权限禁止按钮文本资源ID
     * @return
     */
    int deniedBtnResId() default 0;

    /**
     * app设置按钮文本
     * @return
     */
    String settingText() default "";

    /**
     * app设置按钮文本资源ID
     * @return
     */
    int settingResId() default 0;

    /**
     * 是否显示跳转到应用权限设置界面
     * @return 是否显示跳转到应用权限设置界面
     */
    boolean needGotoSetting() default false;

    /**
     * 是否无视权限，程序正常往下走
     * @return 是否无视权限，程序正常往下走
     */
    boolean runIgnorePermission() default false;
```


### 建议

**1、 建议权限申请的触发时机放在Activity类及Activity，Fragment的成员函数上面，不建议在Service等后台环境中触发；**

**2、 `READ_PHONE_STATE`和`WRITE_EXTERNAL_STORAG`E这两个权限是使用非常频繁的权限，建议在BaseActivity里面申请这两个权；**

**3、 当用户点击home键回到桌面去设置关闭权限，然后再从最近任务里恢复app上一次的界面时，可能会出现权限被漏申请的问题，这个时候建议将`READ_PHONE_STATE`和`WRITE_EXTERNAL_STORAGE`这两个使用非常频繁的权限放到BaseActivity里去申请；**

**4、@NeedPermission可以使用在Application的方法上,当你的app在启动是需要申请大量权限时, 可以用这种方法, 如果用户禁止权限,则不进入app**

**5、 如果不想使用AOP方案控制权限申请逻辑，可以使用下面的方案：**

```
CheckPermission.instance(context).check(permissionItem, new PermissionListener() {
                    @Override
                    public void permissionGranted() {
                        //获取到权限时执行正常业务逻辑
                    }

                    @Override
                    public void permissionDenied() {
                        //权限被拒绝时给用户友好提示
                    }
                });
```


### 危险权限列表

```

CALENDAR:
	READ_CALENDAR
	WRITE_CALENDAR

CAMERA:
	CAMERA

CONTACTS:
	READ_CONTACTS
	WRITE_CONTACTS
	GET_ACCOUNTS
	
LOCATION:	
	ACCESS_FINE_LOCATION
	ACCESS_COARSE_LOCATION

MICROPHONE:	
	RECORD_AUDIO
	
PHONE:
	READ_PHONE_STATE
	CALL_PHONE
	READ_CALL_LOG
	WRITE_CALL_LOG
	ADD_VOICEMAIL
	USE_SIP
	PROCESS_OUTGOING_CALLS

SENSORS:
	BODY_SENSORS

SMS	:
	SEND_SMS
	RECEIVE_SMS
	READ_SMS
	RECEIVE_WAP_PUSH
	RECEIVE_MMS
	
STORAGE:	
	READ_EXTERNAL_STORAGE
	WRITE_EXTERNAL_STORAGE
	
```

