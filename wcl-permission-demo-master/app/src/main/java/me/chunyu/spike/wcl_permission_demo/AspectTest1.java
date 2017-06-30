package me.chunyu.spike.wcl_permission_demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.*;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.service.chooser.ChooserTarget;
import org.android10.gintonic.internal.ChooseDialog;
import org.android10.gintonic.internal.DebugLog;
import org.android10.gintonic.internal.MethodMsg;
import org.android10.gintonic.internal.StopWatch;
import org.aspectj.lang.reflect.MethodSignature;
/**
 * Created by lenovo on 2017/5/16.
 */

@Aspect
public class AspectTest1 {

    private static final String Pointcut_FINDPERMISSION=
            "execution(* andriod.Mainfest.permission.PermissionsChecker.PermissionsChecker(Context))&&args(context)";
    private static final String Pointcut_Checker=
            "execution(* andriod.Mainfest.permission.MainActivity.action(..))";
    private static final String POINTCUT_METHOD =
            "(execution(* *..Activity+.*(..)) ||execution(* *..Layout+.*(..))) && target(Object) && this(Object)";
    public Context thisContext;
    @Pointcut(Pointcut_FINDPERMISSION)
    public void AllPermission(){
    }
    @Before("AllPermisson()")
    public void OnBefore(Context context) throws PackageManager.NameNotFoundException {
        thisContext=context;
        PackageManager pm=thisContext.getPackageManager();
        try {
            PackageInfo pack = pm.getPackageInfo("me.chunyu.spike.wcl_permission_demo", PackageManager.GET_PERMISSIONS);
            String[] permissionStrings = pack.requestedPermissions;
            permissionStrings.toString();
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Pointcut(Pointcut_Checker)
    public void AspectMethod1(){
    }
    @Before()
    @Pointcut("execution(* andriod.content.Intent.*(..))")
    public void extra_information(){

    }
    /**
     * 在截获的目标方法调用之前执行该Advise
     * @param joinPoint
     * @throws Throwable
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Before("methodAnootatedWith()")
    public void onCreateBefore(JoinPoint joinPoint) throws Throwable{
        Activity activity = null;
        //获取目标对象
        activity = ((Activity)joinPoint.getTarget());
        //插入自己的实现，控制目标对象的执行
        ChooserTarget dialog = new ChooseDialog(activity);
        dialog.show();

        //做其他的操作
        buildLogMessage("test",20);
    }
    /**
     * 在截获的目标方法调用返回之后（无论正常还是异常）执行该Advise
     * @param joinPoint
     * @throws Throwable
     */
    @After("methodAnootatedWith()")
    public void onCreateAfter(JoinPoint joinPoint) throws Throwable{
        Log.e("onCreateAfter:","onCreate is end .");

    }
    /**
     * 在截获的目标方法体开始执行时（刚进入该方法实体时）调用
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("methodAnnotated()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {

        if (currentObject == null){
            currentObject = joinPoint.getTarget();
        }
        //初始化计时器
        final StopWatch stopWatch = new StopWatch();
        //开始监听
        stopWatch.start();
        //调用原方法的执行。
        Object result = joinPoint.proceed();
        //监听结束
        stopWatch.stop();
        //获取方法信息对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className;
        //获取当前对象，通过反射获取类别详细信息
        className = joinPoint.getThis().getClass().getName();

        String methodName = methodSignature.getName();
        if (currentObject != null && currentObject.equals(joinPoint.getTarget())){
            DebugLog.log(new MethodMsg(className, buildLogMessage(methodName, stopWatch.getTotalTimeMicros()),stopWatch.getTotalTimeMicros()));
        }else if(currentObject != null && !currentObject.equals(joinPoint.getTarget())){
            DebugLog.log(new MethodMsg(className, buildLogMessage(methodName, stopWatch.getTotalTimeMicros()),stopWatch.getTotalTimeMicros()));
            currentObject = joinPoint.getTarget();
            DebugLog.outPut(new Path());    //日志存储
            DebugLog.ReadIn(new Path());    //日志读取
        }
        return result;
    }

    /**
     * 创建一个日志信息
     *
     * @param methodName 方法名
     * @param methodDuration 执行时间
     * @return
     */
    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        if (StopWatch.Accuracy == 1){
            message.append("ms");
        }else {
            message.append("mic");
        }
        message.append("]      ");

     }
  return message.toString();
}

