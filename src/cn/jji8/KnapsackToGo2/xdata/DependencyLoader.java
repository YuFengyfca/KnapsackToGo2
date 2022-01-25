package cn.jji8.KnapsackToGo2.xdata;


import sun.misc.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
/**
 * 用于加载jar包，代码出自：https://github.com/LovesAsuna/Mirai-Bot/blob/master/src/main/java/me/lovesasuna/bot/Agent.java
 * */
public class DependencyLoader {
    private static final Unsafe UNSAFE;
    private static final MethodHandles.Lookup LOOKUP;
    private static final MethodType METHODTYPE;
    private static final ClassLoader LOADER;
    static {
        try {
            LOADER = DependencyLoader.class.getClassLoader();//ClassLoader.getSystemClassLoader();
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
            METHODTYPE = MethodType.methodType(void.class, URL.class);
            MethodHandles.lookup();
            Field lookupField = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            Object lookupBase = UNSAFE.staticFieldBase(lookupField);
            long lookupOffset = UNSAFE.staticFieldOffset(lookupField);
            LOOKUP = (MethodHandles.Lookup) UNSAFE.getObject(lookupBase, lookupOffset);
        } catch (Throwable t) {
            throw new IllegalStateException("Unsafe not found");
        }
    }
    public static void addToClassPath(Path jarPath) {
        try {
            addToClassPath(jarPath.toUri().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public static void addToClassPath(URL jarPath) {
        try {
            if (LOADER instanceof URLClassLoader) {
                MethodHandle methodHandle = LOOKUP.findVirtual(LOADER.getClass(), "addURL", METHODTYPE);
                methodHandle.invoke(LOADER, jarPath);
            } else {
                Field ucpField = LOADER.getClass().getDeclaredField("ucp");
                long ucpOffset = UNSAFE.objectFieldOffset(ucpField);
                Object ucp = UNSAFE.getObject(LOADER, ucpOffset);
                MethodHandle methodHandle = LOOKUP.findVirtual(ucp.getClass(), "addURL", METHODTYPE);
                methodHandle.invoke(ucp, jarPath);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
