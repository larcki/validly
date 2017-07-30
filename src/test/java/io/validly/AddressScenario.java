package io.validly;

import java.time.LocalDate;
import java.util.function.Predicate;

import static io.validly.NoteFirstValidator.*;
import static java.time.format.DateTimeFormatter.ofPattern;

public class AddressScenario {

    public static final String IS_REQUIRED = "Is required";
    public static final String INVALID = "Invalid value";
    public static final String MUST_BE_PAST = "Must be in the past";
    public static final String MIN_TWO_REQUIRED = "Must have at least two lines";
    public static final String TOO_LONG_LINE = "Too long line";

    public ValidlyNote validate(Address address) {
        ValidlyNote note = new Notification();

        valid(address.getCountry(), "country", note)
                .mustNotBeBlank("Is required");

        valid(address.getState(), "state", note)
                .when(countryRequiresState(address),
                        Then.mustNotBeNull(IS_REQUIRED),
                        Then.must(validForCountry(address), INVALID));

        valid(address.getMoveInDate(), "moveInDate", note)
                .canBeNull()
                .mustConvert(s -> LocalDate.parse(s, ofPattern("dd.MM.yyyy")), INVALID)
                .must(d -> d.isBefore(LocalDate.now()), MUST_BE_PAST);

        valid(address.getAddressLines(), "addressLines", note)
                .mustNotBeNull(IS_REQUIRED)
                .must(lines -> lines.size() >= 2, MIN_TWO_REQUIRED)
                .must(lines -> lines.stream().allMatch(s -> s.length() < 100), TOO_LONG_LINE);

        valid(address.getPostCode(), "postCode", note)
                .mustNotBeBlank(IS_REQUIRED)
                .lengthMustBeWithin(4, 12, INVALID)
                .when(isUsa(address), Then.must(s -> s.matches("us.zipcode.regex"), INVALID))
                .when(isUk(address), Then.must(s -> s.matches("uk.postcode.regex"), INVALID));

        return note;
    }

    private boolean isUsa(Address address) {
        return "us".equals(address.getCountry());
    }

    private Predicate<String> validForCountry(Address address) {
        return null;
    }

    private Predicate<String> beValidUsaState() {
        return s -> s.matches("s");
    }

    private boolean countryRequiresState(Address address) {
        return isUsa(address) || isUk(address);
    }

    private boolean isUk(Address address) {
        return "gb".equals(address.getCountry());
    }

}
