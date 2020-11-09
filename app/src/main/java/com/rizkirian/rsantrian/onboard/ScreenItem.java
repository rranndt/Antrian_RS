package com.rizkirian.rsantrian.onboard;

/**
 * @author Rizki Rian Anandita
 * Create By rizki
 */
public class ScreenItem {

    private String title;
    private String desc;
    private int screenTag;

    public ScreenItem(String title, String desc, int screenTag) {
        this.title = title;
        this.desc = desc;
        this.screenTag = screenTag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getScreenTag() {
        return screenTag;
    }

    public void setScreenTag(int screenTag) {
        this.screenTag = screenTag;
    }
}
