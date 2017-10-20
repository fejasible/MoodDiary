package com.app.feja.mooddiary.model.entity;


import com.app.feja.mooddiary.constant.WEATHER;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@DatabaseTable(tableName = "tb_diary")
public class DiaryEntity extends BaseEntity implements Serializable{

    public static final String BUNDLE_NAME = "DiaryEntity";

    public static final int MIRTHFUL = 0;
    public static final int SMILING = 1;
    public static final int CALM = 2;
    public static final int DISAPPOINTED = 3;
    public static final int SAD = 4;

    public static final Integer IS_DELETE = 0;
    public static final Integer IS_NOT_DELETE = 1;

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "create_time")
    private Date createTime = new Date();

    @DatabaseField(columnName = "mood")
    private Integer mood = CALM;

    @DatabaseField(columnName = "content")
    private String content = "";

    @DatabaseField(columnName = "weather")
    private Integer weather = WEATHER.UNKNOWN.getIndex();

    @DatabaseField(foreign = true, columnName = "type_id")
    private TypeEntity type = new TypeEntity();

    @DatabaseField(columnName = "is_delete")
    private Integer isDelete = IS_NOT_DELETE;

    @DatabaseField(columnName = "background")
    private String background = "";

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

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

}
