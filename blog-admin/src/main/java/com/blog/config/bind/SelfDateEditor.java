package com.blog.config.bind;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SelfDateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        //yyyy-MM-dd 这样格式的
        if (text.matches("^\\d{4}-\\d{2}-\\d{2}$")){
            try {
                setValue(new SimpleDateFormat("yyyy-MM-dd").parse(text));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}
