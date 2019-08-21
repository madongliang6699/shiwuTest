package testConfiguration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * @author ：madongliang
 * @date ：Created in 2019/8/20 16:44
 * @description： 格言：每天生活工作都要全力以赴，绝不偷懒！（尽量吧） 决定一个人的一生以及整个命运的，是日积月累的努力！
 */


public class TestConfiguration {

    public static void main(String[] args) {
        //ApplicationContext context = new ClassPathXmlApplicationContext(String.valueOf(ConfigClass.class));
        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigClass.class);
        DongWu getDongWu = (DongWu)context.getBean("getDongWu");
        System.out.println(getDongWu);
    }


}
