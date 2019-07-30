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
 */
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperDao paperDao;
    @Autowired
    private PaperService2 paperService2;

    /**
     *  @Transactional 要是只在接口上写, 接口的实现类就会继承下来、接口的实现类的具体方法,可以覆盖类声明处的设置
     *
     *   类级的注解、适用于类中所有的public的方法
     */
    @Override
//    @Transactional(propagation = Propagation.REQUIRED/*,isolation = Isolation.READ_COMMITTED,rollbackFor = Exception.class*/)
    @Transactional(propagation = Propagation.MANDATORY)
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
//    @Transactional(propagation = Propagation.NESTED)
//    @Transactional(propagation = Propagation.NEVER)
    public int addPaper(Paper paper) {
        int i = paperDao.addPaper(paper);
        //int i1 = 1 / 0;//人为的制造异常。测试事务
        return i;
    }

    @Override
    public int deletePaperById(long id) {
        int i = paperDao.deletePaperById(id);
        return i;
    }

    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    @Transactional(propagation = Propagation.MANDATORY)
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
     *    另外，即使里面的单个方法是有事务的，但是因为调用这个有事务方法的方法没有事务，胡导致里面两个有事务的方法没起不到事务的作用了，
     *    原因是：【  A方法（没设置事务）调用B方法（有事务）：
     *
     *      spring默认的是PROPAGATION_REQUIRED机制,如果方法A标注了注解@Transactional 是完全没问题的,执行的时候传播给方法B,因为方法A开启了事务,
     *      线程内的connection的属性autoCommit=false,并且执行到方法B时,事务传播依然是生效的,得到的还是方法A的connection,autoCommit还是为false,
     *      所以事务生效;
     *      反之,如果方法A没有注解@Transactional 时,是不受事务管理的,autoCommit=true,那么传播给方法B的也为true,每执行完一条sql语句都是直接自动提交,即使B标注了@Transactional ;

             在一个Service内部，事务方法之间的嵌套调用，普通方法和事务方法之间的嵌套调用，都不会开启新的事务.是因为spring采用动态代理机制来实现事务控制，而动态代理最终都是要调用原始对象的，而原始对象在去调用方法时，是不会再触发代理了！

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
     */

//    @Transactional(propagation = Propagation.REQUIRED)
    public String addupdatePaper(Paper paper1,Paper paper2) {
//        int addId = addPaper(paper1);
        int addId = paperService2.addPaper(paper1);
        int updateId = updatePaper(paper2);
        //int y= 1/0;
        return addId+","+updateId;
    }

}
