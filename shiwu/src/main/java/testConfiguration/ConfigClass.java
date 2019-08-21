package testConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：madongliang
 * @date ：Created in 2019/8/20 16:40
 * @description： 格言：每天生活工作都要全力以赴，绝不偷懒！（尽量吧） 决定一个人的一生以及整个命运的，是日积月累的努力！
 *
 * 总结 ：@Configuration + @Bean 的方式替代了xml加<bean></bean>标签的方式把一个类交给spring容器管理。
 * 这样做的好处是 交给IOC的bean是我们自己创建并控制的。之前数据源bean的管理只能使用传统的xml中<bean>标签的方式，
 * 用@Configuration + @Bean 的方式可以用代码编辑数据源的bean。就是本类的这种方式。
 *
 * 所以@Configuration + @Bean的方式就是springboot中添加配置的最常用方法，实现了去配置文件xml的目的。
 *
 */
@Configuration
public class ConfigClass {

    @Bean
    public DongWu getDongWu(){
        return new DongWu("cat");
    }

}
