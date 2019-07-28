package com.ssm.controller;

import com.ssm.entity.Paper;
import com.ssm.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;

/**
 * @author ：madongliang
 * @date ：Created in 2019/7/27 1:45
 * @description： 格言：每天生活工作都要全力以赴，绝不偷懒！（尽量吧） 决定一个人的一生以及整个命运的，是日积月累的努力！
 */
//@RequestMapping("/paper")
@Controller
public class PaperController {
    @Autowired
    private PaperService paperService;

    @ResponseBody
    @RequestMapping(value = "/allPaper", produces = "application/json; charset=utf-8")
    public Object list() {
        List<Paper> list = paperService.queryAllPaper();
        System.out.println(list.toString());
        return list.toString();
    }



    @RequestMapping(value = "/add", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String addPaper() {

        Random rand = new Random();


        Paper paper = new Paper();
        paper.setPaperDetail("haha");
        //paper.setPaperId(5);
        paper.setPaperName("我的书名字");
        paper.setPaperNum(rand.nextInt(100) + 1);
        paperService.addPaper(paper);
        return "插入一条数据";
    }

    @RequestMapping(value = "/del/{paperId}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String deletePaper(@PathVariable("paperId") Long id) {
        paperService.deletePaperById(id);
        return "删除一条id是"+id+"数据";
    }



    @RequestMapping(value = "/update/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String updatePaper(@PathVariable("id") Long id) {
        Random rand = new Random();

        Paper paper = new Paper();
        paper.setPaperDetail("haha");
        paper.setPaperId(id);
        paper.setPaperName("我的书名字");
        paper.setPaperNum(rand.nextInt(100) + 1);
        paperService.updatePaper(paper);
        //paper = paperService.queryById(paper.getPaperId());
        return "修改了id是"+id+"的数据";
    }

}
