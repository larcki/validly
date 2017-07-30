Validly - Validation library for Java 8
=======================================
[![Build Status](https://travis-ci.org/larcki/validly.svg?branch=master)](https://travis-ci.org/larcki/validly)

Validly provides a clean and convenient way of implementing validation logic by abstracting away the conditional constructs and imperative nature of Java. 

Validly allows you to:

* Focus on expressing the validation rules in declarative way instead of writing an if-else mess.
* Use [Notification pattern](https://martinfowler.com/articles/replaceThrowWithNotification.html) to capture all the validation errors.

Using Validly
-------------

#### Basics #####
```java
import static com.validly.FailFastValidator.*;

public class Validator {

    public void validate(String input) throws ValidationErrorException {
        valid(input)
            .mustNotBeNull("It's null")
            .lengthMustBeAtLeast(2, "It's too short")
            .mustStartWith("Hello", "It doesn't start properly")
            .must(s -> myOwnRule(s), "It doesn't match my own rule");
    }
}
```
Validly has three different validation modes. 

1. **Fail-Fast**: throws a ValidationErrorException when validation error occurs. (Example above)
2. **Note-First**: gathers the first error of each value into a List or Notification object.
3. **Note-All**: gathers all the errors of each value into a List or Notification object. 

Fail-Fast mode is ideal when validating one input value. [Replacing throwing exceptions with notification](https://martinfowler.com/articles/replaceThrowWithNotification.html) makes sense if you want to report more than just the first occurring validation error - ideal when validating domain objects:
```java
// If you want to report every error of each field use NoteAllValidator
import static com.validly.NoteFirstValidator.*; 

public class CustomerScenario {

    public Notification validate(Customer customer) {
        Notification note = new Notification(); // You can also use List

        valid(customer.getName(), "name", note)
                .mustNotBeBlank("Can't be blank")
                .lengthMustNotExceed(20, "Too long");

        valid(customer.getAge(), "age", note)
                .mustNotBeNull("Can't be null")
                .valueMustBeAtLeast(0, "Can't be negative");

        valid(customer.getSsn(), "ssn", note)
                .mustNotBeNullWhen(customer.getAge() >= 18, "Required for adults")
                .must(s -> s.matches("//your.regex+"), "Invalid format");

        return note;
    }
}
```
#### Conditions and conversions #####
```java
import static io.validly.NoteFirstValidator.*;

public class AddressScenario {

    public ValidlyNote validate(Address address) {
        ValidlyNote note = new Notification();

        valid(address.getCountry(), "country", note)
                .mustNotBeBlank("Is required");

        valid(address.getState(), "state", note)
                .when(countryRequiresState(address), // when takes one or more Then-predicates
                        Then.mustNotBeNull("Is required"), // both are evaluated only if when is true
                        Then.must(validForCountry(address), "Invalid value"));

        // convert to LocalDate and use that type after conversion
        valid(address.getMoveInDate(), "moveInDate", note)
                .canBeNull()
                .mustConvert(s -> LocalDate.parse(s, ofPattern("dd.MM.yyyy")), "Invalid value") 
                .must(d -> d.isBefore(LocalDate.now()), "Must be in the past");

        // input type is inferred (List<String> in this case) and usable for must-predicates
        valid(address.getAddressLines(), "addressLines", note)
                .mustNotBeNull("Is required")
                .must(lines -> lines.size() >= 2, "min two required")
                .must(lines -> lines.stream().allMatch(s -> s.length() < 100), "must be under 100 chars");

        valid(address.getPostCode(), "postCode", note)
                .mustNotBeBlank("is required")
                .lengthMustBeWithin(4, 12, "invalid value")
                .when(isUsa(address), Then.must(s -> s.matches("us.zipcode.regex"), "invalid value"))
                .when(isUk(address), Then.must(s -> s.matches("uk.postcode.regex"), "invalid value"));

        return note;
    }
    
    private boolean countryRequiresState(Address address) {
        return isUsa(address) || isUk(address);
    }
    /* Omitted rest of the methods and constants */
}
```
#### Notification #####

#### DSL with Validly ####
Create a clean DSL for your validation by naming the custom predicates to be in line with the Validly's must-convention:
```java
import static io.validly.NoteAllValidator.valid;

public class PasswordScenario {

    public List<String> validate(PasswordChangeRequest passwordChangeRequest) {
        List<String> note = new ArrayList<>();

        valid(passwordChangeRequest.getNewPassword(), note)
                .mustNotBeBlank("Is null or empty")
                .lengthMustBeAtLeast(6, "Too short")
                .lengthMustNotExceed(24, "Too long")
                .must(notContain(" "), "Contains space")
                .must(containDigits() // Using Predicate's and-method to compose two predicates
                        .and(containLetters()), "Doesn't contain numbers and letters")
                .must(notBlacklisted(), "Blacklisted value");
                
        valid(passwordChangeRequest.getCurrentPassword(), note)
                .must(equalActualCurrentPassword(), "Invalid current password");

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
    private Predicate<String> equalActualCurrentPassword() {
        return customerService::passwordEquals;
    }
}
```




