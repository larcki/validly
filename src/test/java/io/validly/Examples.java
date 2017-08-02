package io.validly;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static io.validly.NoteFirstValidator.valid;
import static java.time.format.DateTimeFormatter.ofPattern;

public class Examples {

    public static final String IS_REQUIRED = "Is required";
    public static final String INVALID = "Invalid value";
    public static final String MUST_BE_PAST = "Must be in the past";
    public static final String MIN_TWO_REQUIRED = "Must have at least two lines";
    public static final String TOO_LONG_LINE = "Too long line";
    public static final List<String> BLACKLIST = Arrays.asList("password123", "123password");

    private CustomerService customerService;

    public ValidlyNote validateAddress(Address address) {
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

    public Notification validateCustomer(Customer customer) {
        Notification note = new Notification();

        valid(customer.getName(), "name", note)
                .mustNotBeBlank("Can't be blank")
                .lengthMustNotExceed(20, "Too long");

        valid(customer.getAge(), "age", note)
                .mustNotBeNull("Can't be null")
                .valueMustBeAtLeast(0, "Can't be negative");

        valid(customer.getSsn(), "ssn", note)
                .mustNotBeNullWhen(customer.getAge() >= 18, "Required for adults")
                .must(this::validSsn, "Invalid format");

        return note;
    }

    public List<String> validateCustomer(PasswordChangeRequest passwordChangeRequest) {
        List<String> note = new ArrayList<>();

        NoteAllValidator.valid(passwordChangeRequest.getNewPassword(), note)
                .mustNotBeBlank("Is null or empty")
                .lengthMustBeAtLeast(6, "Too short")
                .lengthMustNotExceed(24, "Too long")
                .must(notContain(" "), "Contains space")
                .must(containDigits()
                        .and(containLetters()), "Doesn't contain numbers and letters")
                .must(notBlacklisted(), "Blacklisted value");

        NoteAllValidator.valid(passwordChangeRequest.getCurrentPassword(), note)
                .must(equalCurrentPassword(), "Invalid current password");

        return note;
    }

    private Predicate<String> notBlacklisted() {
        return BLACKLIST::contains;
    }

    private Predicate<String> containDigits() {
        return s -> s.matches(".*\\d.*");
    }

    private Predicate<String> containLetters() {
        return s -> s.matches(".*[a-zA-Z]+.*");
    }

    private Predicate<String> notContain(CharSequence... value) {
        return s -> !Arrays.asList(value).contains(s);
    }

    private Predicate<String> equalCurrentPassword() {
        return customerService::passwordEquals;
    }

    private interface PasswordChangeRequest {

        String getCurrentPassword();

        String getNewPassword();
    }

    private interface CustomerService {

        boolean passwordEquals(String password);

    }

    private boolean validSsn(String s) {
        return s.matches("//your.regex+");
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
