package myAOPtest.B_JDK的动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Interceptor implements InvocationHandler {

    private Object mubiaoClass;//目标类 PersonDao
    private Transaction transaction;//增强类

    public Interceptor(Object mubiaoClass, Transaction transaction) {
        this.mubiaoClass = mubiaoClass;
        this.transaction = transaction;
    }

    /**
     * @param proxy  目标对象的被代理的类的实例 【例如：new的PersonDaoImpl()】
     * @param method 被代理的类的实例 的被调用接口方法的Method实例
     *               【例如：PersonDao里的savePerson方法的Method实例，这个实例应该是背后自动生成的，那么是根据什么生成的？
     *               看这里应该是根据我们传入的上一个参数 目标对象的实例 里的方法。】
     * @param args   传入到代理实例上方法参数值的对象数组 【这里应该就是savePerson方法的参数的数组，只不过这里savePerson方法没有参数而已】
     * @return 方法的返回值，没有返回值是null
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {/**这个method实例里存放的方法名字是被代理类的方法，应该是从前面那个被代理类proxy中自动获取并封装成的（背后用了上面方法获取的，反射吗？）并且下面method.getName()可以看出，这里面每次只封装一个 被代理类的方法*/
        String methodName = method.getName();

        if ("savePerson".equals(methodName) || "updatePerson".equals(methodName) || "delPerson".equals(methodName)) {
            this.transaction.beginTransaction();
            method.invoke(mubiaoClass);//这底层用什么方式调用的，反射吗？
            this.transaction.commit();
        } else {
            method.invoke(mubiaoClass);
        }

        return null;
    }

    /**
     * 通过上面这种方式，我们就只需要指定出入目标类和增强类，目标类里的方法就不用每个都重写了
     *
     *总结：
         1、因为利用JDKProxy生成的代理类实现了接口，所以目标类中所有的方法在代理类中都有。
         2、生成的代理类的所有的方法都拦截了目标类的所有的方法。而拦截器中invoke方法的内容正好就是代理类的各个方法的组成体。
         3、利用JDKProxy方式必须有接口的存在。
         4、invoke方法中的三个参数可以访问目标类的被调用方法的API、被调用方法的参数、被调用方法的返回类型。
     缺点：
         1、在拦截器中除了能调用目标对象的目标方法以外，功能是比较单一的，在这个例子中只能处理事务
         2、拦截器中的invoke方法的if判断语句在真实的开发环境下是不靠谱的，因为一旦方法很多if语句中的条件需要写很多。
     *
     *
     * 本动态代理测试是参考了文档 https://www.cnblogs.com/moxiaotao/p/9322234.html
     */

}
