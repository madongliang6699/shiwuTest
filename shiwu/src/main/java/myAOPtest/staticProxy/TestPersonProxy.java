package myAOPtest.staticProxy;

import org.junit.jupiter.api.Test;

/**
 * 测试静态代理
 */
public class TestPersonProxy {

    @Test
    public void testSave(){
        PersonDao personDao = new PersonDaoImpl();
        Transaction transaction = new Transaction();
        PersonDaoProxy personDaoProxy = new PersonDaoProxy(personDao,transaction);

        personDaoProxy.savePerson();


        /**
         * ============== 重点 =================
         *
         * 个人感觉，静态代理的模式，就是通过一个类（代理类）把另外两个类（被代理的类 和 具有要切入的额外功能的类）的功能组合到一起的过程。
         *   比如上面的正常业务的类personDao即:被代理类，transaction事务类 是具有要切入的额外功能的类，personDaoProxy是代理类。
         *
         *   personDaoProxy能让其他两个类组合在一起的方法就是 把两个类导入到自己内部作为自己的成员变量，（利用构造函数将目标类和增强类注入）
         *   再通过自己的一个方法（重写的PersonDao的方法），把两个类中的目标方法的功能 根据逻辑 组合到一起。
         *   这样就做的了代理效果，也就是做到了 在不改变源代码的情况下 给我们的personDao业务类额外增加了功能。
         *   这就是AOP面向切面编程的根本思想吧。只不过这种静态代理的缺点就是要写很多代理类和代理类的方法，代理的接口有改动也要跟着做改动，所以需要动态代理了。
         *
         *
         */



    }

}
