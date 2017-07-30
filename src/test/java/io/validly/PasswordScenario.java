package io.validly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static io.validly.NoteAllValidator.valid;

public class PasswordScenario {

    public static final List<String> BLACKLIST = Arrays.asList("password123", "123password");

    private CustomerService customerService;

    public List<String> validate(PasswordChangeRequest passwordChangeRequest) {
        List<String> note = new ArrayList<>();

        valid(passwordChangeRequest.getNewPassword(), note)
                .mustNotBeBlank("Is null or empty")
                .lengthMustBeAtLeast(6, "Too short")
                .lengthMustNotExceed(24, "Too long")
                .must(notContain(" "), "Contains space")
                .must(containDigits()
                        .and(containLetters()), "Doesn't contain numbers and letters")
                .must(notBlacklisted(), "Blacklisted value");

        valid(passwordChangeRequest.getCurrentPassword(), note)
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
}
