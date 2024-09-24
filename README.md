# Wultra JResult

<p align="center"><img src="intro.jpg" alt="Wultra JResult" width="100%" /></p>

![build](https://github.com/wultra/JResult/actions/workflows/build.yml/badge.svg)
[![jcenter](https://img.shields.io/maven-central/v/com.wultra.android.utilities/jresult)](https://mvnrepository.com/artifact/com.wultra.android.mtokensdk/wultra-mtoken-sdk)
![date](https://img.shields.io/github/release-date/wultra/jresult)
[![license](https://img.shields.io/github/license/wultra/jresult)](LICENSE)

A Java-enabled wrapper for the kotlin.Result that acts as a friendly bridge.

## Integration

Add dependency to your Gradle project.

```groovy
dependencies {
    implementation("com.wultra.android.utilities:jresult:1.0.1")
}
```

## Example usage

Imagine having a 3rd party service that provides a list of users asynchronously with a Result-like based API, that returns kotlin.Result object via lambda function you provided

```java
myService.fetchUsers( result -> {
    new JResult<List<User>>(result)
        .onFailure( exception -> {
            // failed to retrieve the list
            // process exception
            return null;
        })
        .onSuccess( users -> {
             // process users of List<User> type
             return null
        });
    return null; // end of fetchUsers
});
```

## Available methods

```java

// Wrapping the result
JResult<MySuccessClass> jresult = new JResult<>(result); // result is of type kotlin.Result

// simple success test
Boolean success = jresult.isSuccess();

// simple failure test
Boolean failure = jresult.isFailure();

// getting the success object or null
MySuccessClass mySuccessObject = (MySuccessClass) jresult.getOrNull();

// getting the error or null
Throwable error = jresult.exceptionOrNull();


try {
    // gets the object or throws an exception
    jresult.getOrThrow();
} catch (Exception e) {
    // resolve error
}

// lambda-friendly processing
jresult
    .onSuccess( myObject -> {
        // process my object
        return null;
    })
    .onFailure( exception -> {
        // process list exception
        return null;
    });
```

## Why JResult

When processing kotlin.Result<T> in Java, you might find yourself baffled with ` java.lang.ClassCastException` when trying to process the Result. This is because the Result class in Kotlin is a [value class](https://kotlinlang.org/docs/inline-classes.html).

This class unboxes the underlying object for you to make things easier in Java.

> [!INFO]  
> kotlin.Result is a value class made for better performance. This wrapper was made to create a simple bridge option between Kotlin and Java with no regard for performance.
> The impact on the performance of this wrapper will be absolutely insignificant in most cases, but consider yourself warned :)
> This project was made for our internal purposes and will be maintained as such.

## License

All sources are licensed using the Apache 2.0 license. You can use them with no restrictions. If you are using this library, please let us know. We will be happy to share and promote your project.

## Contact

If you need any assistance, do not hesitate to drop us a line at [hello@wultra.com](mailto:hello@wultra.com) or our official [gitter.im/wultra](https://gitter.im/wultra) channel.

### Security Disclosure

If you believe you have identified a security vulnerability with Wultra Mobile Token SDK, you should report it as soon as possible via email to [support@wultra.com](mailto:support@wultra.com). Please do not post it to the public issue tracker.