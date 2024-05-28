package org.example;
import java.util.Date;

public class reports
{
    private int order_id;
    private Date compl_datetime;
    private short grade;
    private String comment_cl;
    private String result;

    public reports(int order_id, Date compl_datetime, short grade, String comment_cl, String result) {
        this.order_id = order_id;
        this.compl_datetime = compl_datetime;
        this.grade = grade;
        this.comment_cl = comment_cl;
        this.result = result;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Date getCompl_datetime() {
        return compl_datetime;
    }

    public void setCompl_datetime(Date compl_datetime) {
        this.compl_datetime = compl_datetime;
    }

    public short getGrade() {
        return grade;
    }

    public void setGrade(short grade) {
        this.grade = grade;
    }

    public String getComment_cl() {
        return comment_cl;
    }

    public void setComment_cl(String comment_cl) {
        this.comment_cl = comment_cl;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
