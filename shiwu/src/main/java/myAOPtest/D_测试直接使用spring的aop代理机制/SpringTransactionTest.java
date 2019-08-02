package myAOPtest.D_测试直接使用spring的aop代理机制;


import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试spring动态代理
 *
 */
public class SpringTransactionTest {

    /**
     * spring AOP代理机制：
     　　1、若目标对象实现了若干接口，spring使用JDK的java.lang.reflect.Proxy类代理。
     　　　　 优点：因为有接口，所以使系统更加松耦合
     　　　　  缺点：为每一个目标类创建接口
     　　2、若目标对象没有实现任何接口，spring使用CGLIB库生成目标对象的子类。
     　　　　  优点：因为代理类与目标类是继承关系，所以不需要有接口的存在。
     　　　　  缺点：因为没有使用接口，所以系统的耦合性没有使用JDK的动态代理好。
     *
     *
     * 这里的springAOP的代理测试用到了接口，使用的应该是JDK的动态代理，那么问题来了：
     *   jdk的动态代理要实现InvocationHandler，里面的invoke方法里的if判断里的方法名字是怎么加进去的？（详情看“B_JDK的动态代理”包里的Interceptor类）
     *   spring用了什么技术
     *
     */

    @Test
    public void testSave(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:AOPtestXML/myTest.xml");
        PersonDao personDao = (PersonDao) context.getBean("personDao");
        personDao.savePerson();
    }


}
