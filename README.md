Validly - Validation library for Java 8
=======================================

Validly is an abstraction of the conditional constructs of the language that provides convenience methods for the most commonly used validation conditions.

* Focus on expressing the validation rules instead of writing an if-else mess.
* Use [Notification pattern](https://martinfowler.com/articles/replaceThrowWithNotification.html) to capture all the validation failures.

Using Validly
-------------

There are three different ways of using Validly. 

1. Note First mode: gathers the first failure of each field into a List or Notification object.
2. Note All mode: gathers all the failures of each field into a List or Notification object. 
3. Fail-Fast mode: throws a ValidationFailureException when validation failure occurs.

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


