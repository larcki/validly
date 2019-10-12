Validly - Validation library for Java 8
=======================================
[![Build Status](https://travis-ci.org/larcki/validly.svg?branch=master)](https://travis-ci.org/larcki/validly)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.validly/Validly/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.validly/Validly)


Validly provides a clean and convenient way for implementing validation logic by providing a fluent API and abstracting away the conditional constructs of the language.

Validly allows you to:

* Focus on expressing the validation rules in a declarative way instead of writing an if-else mess.
* Use <a href="https://martinfowler.com/articles/replaceThrowWithNotification.html" target="_blank">Notification pattern</a> to report all the validation errors.
* Write your own domain specific validation language by extending Validly.

Using Validly
-------------
```xml
<dependency>
    <groupId>io.validly</groupId>
    <artifactId>Validly</artifactId>
    <version>0.1.0</version>
</dependency>
```
### Basics ###
```java
import static io.validly.FailFastValidator.*;

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
Validation rule is defined by passing the value to the valid-method and providing a set of <a href="https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html" target="_blank">Predicates</a>. The library provides predefined predicates (mustNotBeNull, lengthMustBeAtLesast etc.) but custom predicates can be used in with the must-method.

#### Modes ####
Validly has three different validation modes. 

1. **Fail-Fast**: throws a ValidationErrorException when validation error occurs. (Example above)
2. **Note-First**: gathers the first error of each value into a List or Notification object.
3. **Note-All**: gathers all the errors of each value into a List or Notification object. 

Fail-Fast mode is ideal when validating one input value. <a href="https://martinfowler.com/articles/replaceThrowWithNotification.html" target="_blank">Replacing throwing exceptions with notification</a> makes sense if you want to report more than just the first occurring validation error - ideal when validating domain objects:
```java
// If you want to report every error of each field use NoteAllValidator
import static io.validly.NoteFirstValidator.*; 

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
### Conditions and conversions ###
#### When ####
Build a conditional validation with **when**-method by providing a boolean and at least one **Then** predicate. Then will only be evaluated if the preceding boolean is true.
```java
valid(address.getState(), note)
    .when(countryRequiresState(address),
        Then.mustNotBeNull("Is required"),
        Then.must(validStateForCountry(address), "Invalid value"));
```
#### mustConvert ####
Convert the input type by providing a Function to **mustConvert**-method. The new type is usable in the subsequent predicates. If the provided conversion Function throws an Exception or returns a null value, a validation error occurs and the subsequent predicates will not be evaluated (even in Note-All mode).
```java
valid(address.getMoveInDate(), note)
    .canBeNull()
    .mustConvert(s -> LocalDate.parse(s, ofPattern("dd.MM.yyyy")), "Invalid value") 
    .must(d -> d.isBefore(LocalDate.now()), "Must be in the past");
```
#### Type inference ####
Validation engine always infers the type of the provided input. In this case the type is List\<String\>:
```java
valid(address.getAddressLines(), note)
    .mustNotBeNull("Is required")
    .must(lines -> lines.size() >= 2, "min two required") // input type is usable here
    .must(lines -> lines.stream().allMatch(s -> s.length() < 100), "must be under 100 chars");
```
### Notification ###

### DSL with Validly ###
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




