package myAOPtest.C_CGLIB的动态代理;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Interceptor implements MethodInterceptor {

    private Object mubiaoClass;// PersonDaoImpl
    private Transaction transaction;// Transaction

    public Interceptor(Object mubiaoClass, Transaction transaction) {
        this.mubiaoClass = mubiaoClass;
        this.transaction = transaction;
    }

    /**
     * 创建目标对象的代理对象
     *
     * @return
     */
    public Object createProxy() {
        // 代码增强
        Enhancer enhancer = new Enhancer(); // 该类用于生成代理对象
        enhancer.setCallback(this); // 参数为拦截器
        enhancer.setSuperclass(mubiaoClass.getClass());// 设置父类
        return enhancer.create(); // 创建代理对象
    }

    /**
     * @param o 目标对象代理类的实例
     * @param method 代理实例上 调用父类方法的Method实例
     * @param objects 传入到代理实例上方法参数值的对象数组
     * @param methodProxy 使用它调用父类的方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        this.transaction.beginTransaction();
        method.invoke(mubiaoClass);
        this.transaction.commit();

        return null;
    }

    /**
     * 这种动态代理模式没有指定哪些方法被增强功能，测试好像是每一个目标类方法都被增强了增强类的功能，要是只想部分方法被增强怎么办？
     */

}
