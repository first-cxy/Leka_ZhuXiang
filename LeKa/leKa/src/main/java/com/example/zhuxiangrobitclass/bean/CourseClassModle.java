/**
 * 
 */
package com.example.zhuxiangrobitclass.bean;

/**
 * @author fengxb 2014Äê10ÔÂ13ÈÕ
 */
public class CourseClassModle {
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
     * @return the kemu_ID
     */
    public int getKemu_ID() {
        return Kemu_ID;
    }

    /**
     * @param kemu_ID
     *            the kemu_ID to set
     */
    public void setKemu_ID(int kemu_ID) {
        Kemu_ID = kemu_ID;
    }

    private int id;
    private String name;
    private int Kemu_ID;
}
