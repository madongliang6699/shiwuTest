package com.ssm.entity;

/**
 * @author ：madongliang
 * @date ：Created in 2019/7/27 1:15
 * @description：
 */
public class Paper {
    private long paperId;
    private String paperName;
    private int paperNum;
    private String paperDetail;

    public long getPaperId() {
        return paperId;
    }

    public void setPaperId(long paperId) {
        this.paperId = paperId;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public int getPaperNum() {
        return paperNum;
    }

    public void setPaperNum(int paperNum) {
        this.paperNum = paperNum;
    }

    public String getPaperDetail() {
        return paperDetail;
    }

    public void setPaperDetail(String paperDetail) {
        this.paperDetail = paperDetail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Paper paper = (Paper) o;

        if (paperId != paper.paperId) return false;
        if (paperNum != paper.paperNum) return false;
        if (paperName != null ? !paperName.equals(paper.paperName) : paper.paperName != null) return false;
        return paperDetail != null ? paperDetail.equals(paper.paperDetail) : paper.paperDetail == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (paperId ^ (paperId >>> 32));
        result = 31 * result + (paperName != null ? paperName.hashCode() : 0);
        result = 31 * result + paperNum;
        result = 31 * result + (paperDetail != null ? paperDetail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "paperId=" + paperId +
                ", paperName='" + paperName + '\'' +
                ", paperNum=" + paperNum +
                ", paperDetail='" + paperDetail + '\'' +
                '}';
    }


}
