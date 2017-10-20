package com.app.feja.mooddiary.model.entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "tb_diary")
public class DiaryEntity extends BaseEntity{

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "create_time")
    private Date createTime;

    @DatabaseField(columnName = "mood")
    private Integer mood;

    @DatabaseField(columnName = "content")
    private String content;

    @DatabaseField(columnName = "weather")
    private Integer weather;

    @DatabaseField(columnName = "type_id")
    private Integer typeId;

    @DatabaseField(columnName = "is_delete")
    private Integer isDelete;

    @DatabaseField(columnName = "background")
    private String background;

    @DatabaseField(columnName = "text_size")
    private Integer textSize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMood() {
        return mood;
    }

    public void setMood(Integer mood) {
        this.mood = mood;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getWeather() {
        return weather;
    }

    public void setWeather(Integer weather) {
        this.weather = weather;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Integer getTextSize() {
        return textSize;
    }

    public void setTextSize(Integer textSize) {
        this.textSize = textSize;
    }
}
