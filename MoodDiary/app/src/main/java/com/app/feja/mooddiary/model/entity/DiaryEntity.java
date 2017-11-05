package com.app.feja.mooddiary.model.entity;


import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.adapter.WeatherAdapter;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@DatabaseTable(tableName = "tb_diary")
public class DiaryEntity extends BaseEntity implements Serializable{

    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TYPE = "type";
    public static final String COLUMN_NAME_CREATE_TIME = "create_time";
    public static final String COLUMN_NAME_MOOD = "mood";
    public static final String COLUMN_NAME_CONTENT = "content";
    public static final String COLUMN_NAME_WEATHER = "weather";
    public static final String COLUMN_NAME_IS_DELETE = "is_delete";
    public static final String COLUMN_NAME_BACKGROUND = "background";
    public static final String COLUMN_NAME_TEXT_SIZE = "text_size";

    public DiaryEntity() {
        this.type = new TypeEntity(ApplicationContext.getContext().getResources().getString(R.string.no_sort));
        this.createTime = new Date();
        this.mood = DiaryEntity.CALM;
        this.content = "";
        this.weather = WeatherAdapter.Unknown;
        this.isDelete = IS_NOT_DELETE;
        this.background = "";
        this.textSize = 16;
    }

    public DiaryEntity(String content, TypeEntity type) {
        this.createTime = new Date();
        this.mood = DiaryEntity.CALM;
        this.content = content;
        this.weather = WeatherAdapter.Unknown;
        this.type = type;
        this.isDelete = IS_NOT_DELETE;
        this.background = "";
        this.textSize = 10;
    }

    public static final String BUNDLE_NAME = "DiaryEntity";

    public static final int MIRTHFUL = 0;
    public static final int SMILING = 1;
    public static final int CALM = 2;
    public static final int DISAPPOINTED = 3;
    public static final int SAD = 4;

    public static final Integer IS_DELETE = 0;
    public static final Integer IS_NOT_DELETE = 1;

    @DatabaseField(generatedId = true, canBeNull = false, unique = true)
    private Integer id;

    @DatabaseField(columnName = COLUMN_NAME_CREATE_TIME, canBeNull = false)
    private Date createTime;

    @DatabaseField(columnName = COLUMN_NAME_MOOD, canBeNull = false)
    private Integer mood = CALM;

    @DatabaseField(columnName = COLUMN_NAME_CONTENT, canBeNull = false)
    private String content;

    @DatabaseField(columnName = COLUMN_NAME_WEATHER, canBeNull = false)
    private Integer weather;

    @DatabaseField(foreign = true, canBeNull = false, columnName = COLUMN_NAME_TYPE,
            foreignAutoCreate = true, foreignColumnName = TypeEntity.COLUMN_ID, foreignAutoRefresh = true)
    private TypeEntity type;

    @DatabaseField(columnName = COLUMN_NAME_IS_DELETE, canBeNull = false)
    private Integer isDelete;

    @DatabaseField(columnName = COLUMN_NAME_BACKGROUND, canBeNull = false)
    private String background;

    @DatabaseField(columnName = COLUMN_NAME_TEXT_SIZE, canBeNull = false)
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
