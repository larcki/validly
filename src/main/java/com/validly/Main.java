package com.validly;

public class Main {

    public static void main(String... args) {

//        field("date")
//            .after(now("MUST_BE_IN_FUTURE"))
//            .before(tomrrow("TOO_LATE"));
//
//        field("email")
//            .when(otherFieldProvided())
//                .then(required("CAN_NOT_BE_NULL"), notEmptyString("CAN_NOT_BE_EMPTY"))
//            .minLenght(1)
//            .checkMaxValue(6);
//
//        field("state")
//            .when(countryIsUs())
//                .then(required("CAN_NOT_BE_NULL"), notEmptyString("CAN_NOT_BE_EMPTY"))
//            .minLenght(1)
//            .checkMaxValue(6)
//
//
//        if (note.hasErrors()) {
//            throw new BadRequestException(note);
//        }




    }

    private static boolean countryIsUs() {
        return false;
    }

    private static boolean otherFieldProvided() {
        return false;
    }


    interface Valsidly<T> {

        static OnValidly field(String field) {
            return new OnValidly() {

            };
        }

        static OnValidly notNull(String field) {
            return new OnValidly() {

            };
        }

        static OnValidly notEmpty(String field) {
            return new OnValidly() {

            };
        }

    }

    interface OnValidly {

        default OnValidly after(OnValidly validlyCondition) {
            return this;
        }

        default OnValidly notNull() {
            return this;
        }

        default OnCondition when(boolean validate) {
            return new OnCondition() {
            };
        }
    }

    interface OnCondition {
        default OnValidly then(OnValidly... onValidlies) {
            return new OnValidly() {

            };
        }
    }

}
