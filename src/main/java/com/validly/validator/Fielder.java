package com.validly.validator;

public class Fielder<T, FV extends FieldValidator2> {

    FieldValidator2<T, FV> fieldValidator2;

    public Fielder(FieldValidator2<T, FV> fieldValidator2) {
        this.fieldValidator2 = fieldValidator2;
    }

    public FV isRequired() {
        return (FV) fieldValidator2;
    }

    public static Fielder field(String fieldName, String value, ValidlyNote note) {
        FieldValidator2.StringFieldValidator stringFieldValidator = new FieldValidator2.StringFieldValidator(fieldName, value, note);
        return new Fielder<>(stringFieldValidator);
    }

    public static FieldValidator2.IntegerFieldValidator field(String fieldName, Integer value, ValidlyNote note) {
        return new FieldValidator2.IntegerFieldValidator(fieldName, value, note);
    }


}
