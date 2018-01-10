package com.app.feja.mooddiary.model.entity;

import com.app.feja.mooddiary.constant.CONSTANT;

import java.io.File;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by fejasible@163.com
 */

public class DiaryParser {

    private DiaryEntity diaryEntity;

    public DiaryParser(DiaryEntity diaryEntity) {
        this.diaryEntity = diaryEntity;
    }


    public ContentIterator getIterator(){
        return new ContentIterator();
    }

    /**
     * 日记内容迭代器，迭代内容为文字或图片
     */
    public class ContentIterator implements Iterator<Element>{

        String s;
        String[] split;
        Matcher matcher;
        int i;

        ContentIterator() {
            s = diaryEntity.getContent() + "";
            split = s.split(CONSTANT.EDITABLE_IMAGE_TAG_START + ".*?" + CONSTANT.EDITABLE_IMAGE_TAG_END);
            Pattern pattern = Pattern.compile(CONSTANT.EDITABLE_IMAGE_TAG_START + "(.*?)" +
                    CONSTANT.EDITABLE_IMAGE_TAG_END);
            matcher = pattern.matcher(s);
        }

        @Override
        public boolean hasNext() {
            return s.length() > 0;
        }

        @Override
        public Element next() {
            if(split.length > 0 && s.startsWith(split[i])){
                s = s.substring(split[i].length());
                return new Element(split[i++], String.class);
            }else{
                if(matcher.find()){
                    String path = matcher.group(1);
                    s = s.substring(path.length() + CONSTANT.EDITABLE_IMAGE_TAG_START.length()
                            + CONSTANT.EDITABLE_IMAGE_TAG_END.length());
                    return new Element(new File(path), File.class);
                }else{
                    return new Element("", String.class);
                }
            }
        }
    }


    public class Element{

        private Object object;
        private Class clazz;

        Element(Object object, Class clazz) {
            this.object = object;
            this.clazz = clazz;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }
    }
}
