Validly - Validation library for Java 8
=======================================
[![Build Status](https://travis-ci.org/larcki/validly.svg?branch=master)](https://travis-ci.org/larcki/validly)

Validly provides a clean and convenient way of implementing validation logic by abstracting away the conditional constructs and imperative nature of Java. 

Validly allows you to:

* Focus on expressing the validation rules in declarative way instead of writing an if-else mess.
* Use [Notification pattern](https://martinfowler.com/articles/replaceThrowWithNotification.html) to capture all the validation errors.

Using Validly
-------------

```java
import static com.validly.FailFastValidator.*; // or use NoteAllValidator or NoteFirstValidator

public class HelloWorld {
    public static void main(String[] args) {
        valid(args[0])
            .mustNotBeNull("It's null")
            .lengthMustBeAtLeast(2, "It's too short")
            .mustStartWith("Hello", "It doesn't start properly")
            .must(s -> myOwnRule(s), "It doesn't match my own rule");
    }
}
```
Validly has three different validation modes. 

1. Fail-Fast: throws a ValidationFailureException when validation error occurs.
2. Note-First: gathers the first error of each value into a List or Notification object.
3. Note-All: gathers all the errors of each value into a List or Notification object. 

Examples
-------------



