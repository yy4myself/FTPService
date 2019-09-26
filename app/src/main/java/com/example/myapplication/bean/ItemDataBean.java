package com.example.myapplication.bean;

public class ItemDataBean {

    private int itemImageId;
    private String itemName;
    private String activityClassName;

    public ItemDataBean(int itemImageId, String itemName, String activityClassName) {
        this.itemImageId = itemImageId;
        this.itemName = itemName;
        this.activityClassName = activityClassName;
    }

    public int getItemImageId() {
        return itemImageId;
    }

    public void setItemImageId(int itemImageId) {
        this.itemImageId = itemImageId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getActivityClassName() {
        return activityClassName;
    }

    public void setActivityClassName(String activityClassName) {
        this.activityClassName = activityClassName;
    }
}
