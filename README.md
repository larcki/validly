Validly - Validation library for Java 8
=======================================
[![Build Status](https://travis-ci.org/larcki/validly.svg?branch=master)](https://travis-ci.org/larcki/validly)

Validly provides a clean and convenient way of implementing validation logic by abstracting away the conditional constructs and imperative nature of Java. 

Validly allows you to:

* Focus on expressing the validation rules in declarative way instead of writing an if-else mess.
* Use [Notification pattern](https://martinfowler.com/articles/replaceThrowWithNotification.html) to capture all the validation errors.

Using Validly
-------------

Validly has three different validation modes. 

1. **Fail-Fast**: throws a ValidationFailureException when validation error occurs.
2. **Note-First**: gathers the first error of each value into a List or Notification object.
3. **Note-All**: gathers all the errors of each value into a List or Notification object. 


### Examples ###

#### Basic syntax #####
Fail-Fast mode is ideal when validating one input value.
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
Replacing throwing exceptions with notification makes sense if you want to gather all (or the first) validation errors. Ideal when validating domain objects.
```java
// If you want to gather every error of each field use NoteAllValidator
import static com.validly.NoteFirstValidator.*; 

public class Validator {

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



