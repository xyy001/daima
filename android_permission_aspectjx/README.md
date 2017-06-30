Android_Permission_AspectjX
------------
# 依赖
    compile 'com.firefly1126.
gradle 依赖permissionaspect:permissionaspect:1.0.1'
    compile fileTree(include: ['*.jar'], dir: 'libs')
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:23.3.0'
    compile project(':permissionaspect')
    compile 'com.android.support:support-v13:23.3.0'
    compile 'com.android.support:multidex:1.0.1'
    compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.3'
    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
# 使用
  ## 1. 在Application的onCreate里初始化
```
PermissionCheckSDK.init(Application);
```
  ## 2. 在app层动态添加权限的两种方式

* 使用PermissionCheckSDK.addCheckPermissionItem(CheckPermissionItem item)
* 使用注解的方式添加权限@NeedPermission
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
* @NeedPermission参数说明
     * 你所申请的权限列表，例如 {@link android.Manifest.permission#READ_CONTACTS}
     * @return 权限列表
     * @see android.Manifest.permission
    String[] permissions() default "";
     * 合理性解释内容
     * @return 合理性解释内容
    String rationalMessage() default "";
     * 合理性解释文本资源ID
     * @return
    int rationalMsgResId() default 0;
     * 合理性解释按钮文本
     * @return 合理性解释按钮文本
    String rationalButton() default "";
     * 合理性解释按钮文本资源ID
     * @return
    int rationalBtnResId() default 0;
     * 权限禁止文本内容
     * @return 权限禁止文本内容
    String deniedMessage() default "";
     * 权限禁止文本资源ID
     * @return
    int deniedMsgResId() default 0;
     * 权限禁止按钮文本
     * @return 权限禁止按钮文本
    String deniedButton() default "";
     * 权限禁止按钮文本资源ID
     * @return
    int deniedBtnResId() default 0;
     * app设置按钮文本
     * @return
    String settingText() default "";
     * app设置按钮文本资源ID
     * @return
    int settingResId() default 0;
     * 是否显示跳转到应用权限设置界面
     * @return 是否显示跳转到应用权限设置界面
    boolean needGotoSetting() default false;
    * 是否无视权限，程序正常往下走
     * @return 是否无视权限，程序正常往下走
    boolean runIgnorePermission() default false;



