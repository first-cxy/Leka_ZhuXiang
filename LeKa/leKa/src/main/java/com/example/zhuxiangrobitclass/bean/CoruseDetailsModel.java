/**
 * 
 */
package com.example.zhuxiangrobitclass.bean;

/**
 * @author fengxb 2014Äê10ÔÂ13ÈÕ
 */
public class CoruseDetailsModel {
    private int id;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the course_Id
     */
    public int getCourse_Id() {
        return Course_Id;
    }

    /**
     * @param course_Id
     *            the course_Id to set
     */
    public void setCourse_Id(int course_Id) {
        Course_Id = course_Id;
    }

    private String name;
    private String url;
    private int Course_Id;
}
