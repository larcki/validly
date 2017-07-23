Validly - Validation library for Java 8
=======================================

Validly is an abstraction of the conditional constructs of the language that provides convenience methods for the most commonly used validation conditions.

* Focus on expressing the validation rules instead of writing an if-else mess.
* Use [Notification pattern](https://martinfowler.com/articles/replaceThrowWithNotification.html) to capture all the validation failures.

Using Validly
-------------

There are two different ways of using Validly. 

1. Notification pattern mode: evaluates all the validation rules and gathers the failures into a List or Notification object.
2. Fail-fast mode: throws an exception when the first validation failure happens. 


```java

        field("firstName", customer.getFirstName(), notifications)
                .mustNotBeBlank()
                .lengthMustBeAtLeast(2)
                .lengthMustNotExceed(100);

        field("age", customer.getAge(), notifications)
                .mustNotBeNull()
                .valueMustBeAtLeast(1)
                .valueMustNotExceed(130);

        field("referralCode", customer.getReferralCode(), notifications)
                .canBeNull()
                .mustStartWith("REF")
                .mustContain("-")
                .lengthMustBeWithin(10, 20);

        field("ssn", customer.getSsn(), notifications)
                .mustNotBeNullWhen(customer.getAge() > 18)
                .lengthMustNotExceed(10);

```


