package com.app.feja.mooddiary.model.entity;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * created by fejasible@163.com
 */
@SuppressWarnings("serial")
@DatabaseTable(tableName = "tb_type")
public class TypeEntity extends BaseEntity implements Serializable{

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TYPE_COLOR = "type_color";


    public TypeEntity() {
        this.type = TheApplication.getContext().getResources().getString(R.string.no_sort);
        this.typeColor = 0;
    }

    public TypeEntity(String type) {
        this.type = type;
        this.typeColor = 0;
    }

    @DatabaseField(generatedId = true, canBeNull = false, unique = true)
    private Integer id;

    @DatabaseField(columnName = COLUMN_TYPE, canBeNull = false, unique = true)
    private String type;

    @DatabaseField(columnName = COLUMN_TYPE_COLOR, canBeNull = false)
    private Integer typeColor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTypeColor() {
        return typeColor;
    }

    public void setTypeColor(Integer typeColor) {
        this.typeColor = typeColor;
    }
}
