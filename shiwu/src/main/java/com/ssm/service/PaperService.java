package com.ssm.service;

import com.ssm.entity.Paper;

import java.util.List;

/**
 * @author ：madongliang
 * @date ：Created in 2019/7/27 1:33
 * @description： 格言：每天生活工作都要全力以赴，绝不偷懒！（尽量吧） 决定一个人的一生以及整个命运的，是日积月累的努力！
 */
public interface PaperService {
    int addPaper(Paper paper);

    int deletePaperById(long id);

    int updatePaper(Paper paper);

    Paper queryById(long id);

    List<Paper> queryAllPaper();

}
