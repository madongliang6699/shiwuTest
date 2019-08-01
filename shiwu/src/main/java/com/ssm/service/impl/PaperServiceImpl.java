package com.ssm.service.impl;

import com.ssm.dao.PaperDao;
import com.ssm.entity.Paper;
import com.ssm.service.PaperService;
import com.ssm.service.PaperService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：madongliang
 * @date ：Created in 2019/7/27 1:34
 * @description： 格言：每天生活工作都要全力以赴，绝不偷懒！（尽量吧） 决定一个人的一生以及整个命运的，是日积月累的努力！
 *
 *
 * 【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【
 *
 * 看文章或者听同事偶尔聊事务，似乎在编码中经常要根据业务判断当前数据是否是想要的，如果不是就new一个异常抛出，达到spring事务因异常回滚事务的目的。
 *
 * 】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】】
 *
 * 先来了解一下@Transactional注解事务的特性吧，可以更好排查问题
1、service类标签(一般不建议在接口上)上添加@Transactional，可以将整个类纳入spring事务管理，在每个业务方法执行时都会开启一个事务，不过这些事务采用相同的管理方式。
2、@Transactional 注解只能应用到 public 可见度的方法上。 如果应用在protected、private或者 package可见度的方法上，也不会报错，不过事务设置不会起作用。
3、默认情况下，Spring会对unchecked异常进行事务回滚；如果是checked异常则不回滚。
辣么什么是checked异常，什么是unchecked异常
java里面将派生于Error或者RuntimeException（比如空指针，1/0）的异常称为unchecked异常，其他继承自java.lang.Exception得异常统称为Checked Exception，如IOException、TimeoutException等
辣么再通俗一点：你写代码出现的空指针等异常，会被回滚，文件读写，网络出问题，spring就没法回滚了。然后我教大家怎么记这个，因为很多同学容易弄混，你写代码的时候有些IOException我们的编译器是能够检测到的，说以叫checked异常，你写代码的时候空指针等死检测不到的，所以叫unchecked异常。这样是不是好记一些啦
4、只读事务：
 @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
 只读标志只在事务启动时应用，否则即使配置也会被忽略。
 启动事务会增加线程开销，数据库因共享读取而锁定(具体跟数据库类型和事务隔离级别有关)。通常情况下，仅是读取数据时，不必设置只读事务而增加额外的系统开销。


 二：解决Transactional注解不回滚
 1、检查你方法是不是public的
 2、你的异常类型是不是unchecked异常
 如果我想check异常也想回滚怎么办，注解上面写明异常类型即可
 @Transactional(rollbackFor=Exception.class)
 类似的还有norollbackFor，自定义不回滚的异常
 3、数据库引擎要支持事务，如果是MySQL，注意表要使用支持事务的引擎，比如innodb，如果是myisam，事务是不起作用的
 4、是否开启了对注解的解析
 <tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true"/>
 5、spring是否扫描到你这个包，如下是扫描到org.test下面的包
 <context:component-scan base-package="org.test" ></context:component-scan>
 6、检查是不是同一个类中的方法调用（如a方法调用同一个类中的b方法）
 7、异常是不是被你catch住了
 *
 *
 */
@Service("paperService")
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperDao paperDao;
    @Autowired
    private PaperService2 paperService2;
    @Resource(name="paperService")   //这里注入自身类的接口，只能用@Resource(name="paperService")方式，@Autowired方式报错，应该是注入循环了
    private PaperService paperService;

    /**
     *  @Transactional 要是只在接口上写, 接口的实现类就会继承下来、接口的实现类的具体方法,可以覆盖类声明处的设置
     *
     *   类级的注解、适用于类中所有的public的方法
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED/*,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class*/)
//    @Transactional(propagation = Propagation.MANDATORY)
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
//    @Transactional(propagation = Propagation.NESTED)
//    @Transactional(propagation = Propagation.NEVER)
    public int addPaper(Paper paper) {
        int i = paperDao.addPaper(paper);
        int i1 = 1 / 0;//人为的制造异常。测试事务
        return i;
    }

    @Override
    public int deletePaperById(long id) {
        int i = paperDao.deletePaperById(id);
        return i;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
//    @Transactional(propagation = Propagation.MANDATORY)
    public int updatePaper(Paper paper) {
        int i = paperDao.updatePaper(paper);
        //int y = 1/0;
        return i;
    }

    @Override
    public Paper queryById(long id) {
        return paperDao.queryById(id);
    }

    @Override
    public List<Paper> queryAllPaper() {
        return paperDao.queryAllPaper();
    }

    /**
     * 测试传播行为
     */
    /**
     * //@Transactional(propagation = Propagation.REQUIRED)
     * 1.把这个方法的事务注释掉，那么，没事务的方法调用两个有事务的方法最终有没有事务？？比如这个方法.
     *    答案：实测：这个方法整体就没有了事务，即使1/0报错，也能插入和修改。
     *    另外，即使里面的单个方法是有事务的，但是因为调用这个有事务方法的方法没有事务，会导致里面两个有事务的方法也起不到事务的作用了，
     *    原因是：【  （同一个类中）A方法（没设置事务）调用B方法（有事务）：
     *
     *      spring默认的是PROPAGATION_REQUIRED机制,如果方法A标注了注解@Transactional 是完全没问题的,执行的时候传播给方法B,因为方法A开启了事务,
     *      线程内的connection的属性autoCommit=false,并且执行到方法B时,事务传播依然是生效的,得到的还是方法A的connection,autoCommit还是为false,
     *      所以事务生效;
     *      反之,如果方法A没有注解@Transactional 时,是不受事务管理的,autoCommit=true,那么传播给方法B的也为true,每执行完一条sql语句都是直接自动提交,即使B标注了@Transactional ;

             在一个Service类内部，事务方法之间的嵌套调用，普通方法和事务方法之间的嵌套调用，都不会开启新的事务.是因为spring采用动态代理机制来实现事务控制，而动态代理最终都是要调用原始对象的，而原始对象在去调用方法时，是不会再触发代理了！

             所以以上就是为什么我在没有标注事务注解的方法A里去调用标注有事务注解的方法B而没有事务滚回的原因;
             ---------------------
             原文：https://blog.csdn.net/m0_38027656/article/details/84190949
     *
     *    】
     *
     *  2.如果只在这个方法上加事务，里面调用的两个方法不加事务，结果会怎么样呢？？（按理来说应该正常都有事务）
     *     答案：【 正常有事务作用于这个大方法的各个环节。 updatePaper方法中有异常addPaper方法也会回滚。这就是REQUIRED的语义，整个成为一个方法】
     *
     * 3.三个方法都有事务会怎样？
     *    答案：【 正常回滚，同2. 】
     *
     *
     *    注意：1,2,3的测试都是@Transactional(propagation = Propagation.REQUIRED)的测试。
     *
     *
     *
     * 4.没事务的addupdatePaper调用Propagation.MANDATORY的addPaper为什么不抛出异常？MANDATORY不是没有事务就抛异常吗？ （单独访问addPaper抛异常）
     *    答案：原因 同5 ，实际1，2，3,4,5中的问题都是同一个原因造成的。就是1中说的原因，解决办法也在1中了
     *
     * 5.有事务的addupdatePaper调用Propagation.NEVER的addPaper为什么不抛出异常？NEVER不是 有事务就抛异常吗？
     *    答案：如果是调用本类里面的其他方法，这些传播行为的作用就不灵了，应该是因为动态代理特性的原因，原理待查。调用其他类里的同样的这个内部方法就抛异常了。
     *        比如下面调用了paperService2的addPaper();
     *
     *
     *
     *  结论：spring事务中规定的这些传播方式，只有在调用其他service类中的方法时才起作用，调用本类的方法时这些注解中设置的传播行为不起作用。
     *        本方法中（例如这里的addupdatePaper方法），整个过程中无论调用了本类的几个方法，无论调用的方法上加了什么传播属性，
     *        结果只按照本方法上的事务属性去执行，忽略被调用的本类的其他方法的设置的传播属性，就相当于把其他方法中的代码直接挪到了本方法中。
     *        当然本方法中调用的某个方法是其他类的方法，那这个其他类方法上的设置的转播属性就起作用了。只有本类中的方法的不起作用。
     *
     *
     * 参考文档：（重点）   https://www.cnblogs.com/milton/p/6046699.html
     *
     */

//    @Transactional(propagation = Propagation.REQUIRED)
    public String addupdatePaper(Paper paper1,Paper paper2) {
        //paperService  这里注入自身类的接口，只能用@Resource(name="paperService")方式，@Autowired方式报错，应该是注入循环了
        int addId = paperService.addPaper(paper1);  //int addId = addPaper(paper1)  -->  这样直接调用addPaper这个方法本身的事务也跟着addupdatePaper没事务了，
                                                                                        // 但是通过注入自身的接口调用本类的addPaper方法，addPaper这个方法的事务就有效了。（奇妙吧，这应该还是和AOP动态代理有关，虽说还是调用的本类的方法，
                                                                                        // 但是通过本类的接口调用的话，就相当于调用了别的对象，触发了代理，就通过切面切入了事务。）
//        int addId = paperService2.addPaper(paper1);
        int updateId = paperService.updatePaper(paper2);
        int y= 1/0;
        return addId+","+updateId;
    }

}
