package com.validly;


import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.validly.Validly.*;
import static org.junit.Assert.assertEquals;

public class ValidlyTest {

    @Test
    public void notNull_shouldPutToNote_whenValueIsNull() throws Exception {
        Pojo pojo = new Pojo(null, "arvo22");
        MyNote note = new MyNote();

        field(pojo.getField1(), note)
                .notNull("MUST_NOT_BE_NULL")
                .notEmptyString("MUST_NOT_BE_NULL");

        assertEquals(1, note.messages.size());
    }

    @Test
    public void inferInteger() throws Exception {
        Pojo pojo = new Pojo();
        pojo.setIntegerField(null);
        MyNote note = new MyNote();

        field(pojo.getIntegerField(), note)
                .notNull("MUST_NOT_BE_NULL");
        assertEquals(1, note.messages.size());
    }

    @Test(expected = ClassCastException.class)
    public void notEmptyString_shouldThrowException_whenFieldNotString() throws Exception {
        Pojo pojo = new Pojo();
        pojo.setIntegerField(123);
        MyNote note = new MyNote();

        field(pojo.getIntegerField(), note)
                .notEmptyString("MUST_NOT_BE_NULL");
    }

    class MyNote implements ValidlyNote {

        Map<String, String> messages = new HashMap<>();

        @Override
        public void put(String fieldName, String message) {
            messages.put(fieldName, message);
        }
    }



    class Pojo {
        private String field1;
        private String field2;
        private Integer integerField;

        public Pojo(String field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }

        public Pojo() {
        }

        public String getField1() {
            return field1;
        }

        public String getField2() {
            return field2;
        }

        public Integer getIntegerField() {
            return integerField;
        }


        public void setIntegerField(Integer integerField) {
            this.integerField = integerField;
        }
    }

}