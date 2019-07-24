package sun.com.arouter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

/**
 * @author sunxiaoyun
 * @description Arouter路由
 * @time 19/7/17
 */
public class ARouter {

    private static volatile ARouter aRouter;

    private Context context;

    //装载所有Activity的类对象
    private Map<String, Class<? extends Activity>> activityList;

    /**
     * 将所有Activity添加到map里
     *
     * @param path   路径
     * @param tclass activity
     */
    public void putActivity(String path, Class<? extends Activity> tclass) {
        if (path != null && tclass != null) {
            activityList.put(path, tclass);
        }
    }

    /**
     * 跳转到某个activity
     *
     * @param path
     * @param bundle
     */
    public void startActivity(String path, Bundle bundle) {
        //Activity的class对象
        Class<? extends Activity> aClass = activityList.get(path);
        if (aClass != null) {
            Intent intent = new Intent(context, aClass);
            if (bundle != null)
                intent.putExtra("bundle", bundle);
            context.startActivity(intent);
        }else{
            Log.e("ARouter","is path not find");
        }

    }

    //初始化
    public void init(Application context) {
        this.context = context;
        List<String> classNames = getClassName("sun.com.util");
        try {
            for (String className : classNames) {
                Class<?> aClass = Class.forName(className);
                //判断这个类是否是IRouter的实现类
                if (IRouter.class.isAssignableFrom(aClass)){
                    //创建IRouter实现类的实例
                    IRouter iRouter= (IRouter) aClass.newInstance();
                    //调用pushActivity方法
                    iRouter.pushActivity();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static ARouter getInstance() {
        if (aRouter == null) {
            synchronized (ARouter.class) {
                if (aRouter == null) {
                    aRouter = new ARouter();
                }
            }
        }
        return aRouter;
    }

    /**
     * 获取某个包名下的className
     * @param packageName 包名
     * @return className集合
     */
    private List<String> getClassName(String packageName) {
        //创建一个class对象集合
        List<String> classList = new ArrayList<>();
        String path = null;
        try {
            //通过包名管理器 获取到应用信息类然后获取到apk完整路径
            path = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0).sourceDir;
            //根据APK编译后的完整路径获取到编译后的Dex文件
            DexFile dexFile = new DexFile(path);
            //获得编译后的dex中所有的class
            Enumeration enumeration = dexFile.entries();
            //然后进行遍历
            while (enumeration.hasMoreElements()) {
                //通过遍历获取所有的classd的包名
                String name = (String) enumeration.nextElement();
                //判断包名是否符合
                if (name.contains(packageName)) {
                    //如果符合就添加到classList中
                    classList.add(name);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return classList;
    }

    private ARouter() {
        activityList = new HashMap<>();
    }

}
