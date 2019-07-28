package com.ssm.service.impl;

import com.ssm.dao.PaperDao;
import com.ssm.entity.Paper;
import com.ssm.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：madongliang
 * @date ：Created in 2019/7/27 1:34
 * @description： 格言：每天生活工作都要全力以赴，绝不偷懒！（尽量吧） 决定一个人的一生以及整个命运的，是日积月累的努力！
 */
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperDao paperDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
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
    public int updatePaper(Paper paper) {
        int i = paperDao.updatePaper(paper);
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

}
