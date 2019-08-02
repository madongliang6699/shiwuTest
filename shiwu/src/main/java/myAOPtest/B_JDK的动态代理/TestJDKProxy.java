package myAOPtest.B_JDK的动态代理;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

public class TestJDKProxy {

    @Test
    public void testSave(){
        /**
         * 1、创建一个目标对象
         * 2、创建一个事务
         * 3、创建一个拦截器
         * 4、动态产生一个代理对象
         */
        Object personDao = new PersonDaoImpl();
        Transaction transaction = new Transaction();
        Interceptor interceptor = new Interceptor(personDao,transaction);

        /**
         * 参数一：设置代码使用的类加载器，一般采用跟目标类相同的类加载器
         * 参数二：设置代理类实现的接口，跟目标类使用相同的接口
         * 参数三：设置回调对象，当代理对象的方法被调用时，会调用该参数指定对象的invoke方法
         */

        PersonDao personDao1 = (PersonDao) Proxy.newProxyInstance(personDao.getClass().getClassLoader(), personDao.getClass().getInterfaces(), interceptor);

        personDao1.savePerson();
        System.out.println("======================================");
        personDao1.updatePerson();

    }

}
