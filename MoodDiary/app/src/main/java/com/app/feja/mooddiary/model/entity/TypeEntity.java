package com.app.feja.mooddiary.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@SuppressWarnings("serial")
@DatabaseTable(tableName = "tb_type")
public class TypeEntity extends BaseEntity implements Serializable{

    @DatabaseField(generatedId = true)
    private Integer id;

    @DatabaseField(columnName = "type")
    private String type;

    @DatabaseField(columnName = "type_color")
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
