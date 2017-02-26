package com.validly;


public class Validly<T> {

    static <T> OnValidly field(T field, ValidlyNote note) {
        return new OnValidly<>(field, note);
    }

    static class OnValidly<T> {
        private T field;
        private boolean failure;
        private ValidlyNote note;

        private OnValidly(T field, ValidlyNote note) {
            this.field = field;
            this.note = note;
        }

        public OnValidly notEmptyString(String message) {
            if (!failure) {
                String stringField;
                try {
                    stringField = (String) this.field;
                    if (stringField.isEmpty()) {
                        note.put("", message);
                        failure = true;
                    }
                } catch (ClassCastException e) {
                    throw e;
                }
            }
            return this;
        }

        public OnValidly notNull(String message) {
            if (!failure && field == null) {
                note.put("", message);
                failure = true;
            }
            return this;
        }
    }

}
