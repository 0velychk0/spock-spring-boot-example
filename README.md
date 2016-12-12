# Spock and Spring Boot example

This is an example of Spring Boot app tested with Spock framework. 

- Spring Boot 1.4
- Spock 1.1-rc-3
- Groovy 2.4


## [`example.HelloServiceSpec`](/src/test/groovy/example/HelloServiceSpec.groovy)

This verifies behavior of the component using mock injection.
See also [Spock Spring Module](http://spockframework.org/spock/docs/1.1-rc-3/modules.html).

Mark as a Spring Boot test without the web environment:

```groovy
@SpringBootTest(webEnvironment = NONE)
```

Declare the mock interaction by Spock style:

```groovy
        given:
        1 * client.getDefault() >> new Hello('world')
```

Configure the mock injection:

```groovy
    @TestConfiguration
    static class MockConfig {
        final detachedMockFactory = new DetachedMockFactory()

        @Bean
        ExternalApiClient externalApiClient() {
            detachedMockFactory.Mock(ExternalApiClient)
        }
    }
```


## [`example.integration.HelloServiceSpec`](/src/test/groovy/example/integration/HelloServiceSpec.groovy)

This verifies behavior of the component integrated with dependencies, i.e. without any mock.
Simple and easy solution.


## [`example.e2e.HelloServiceSpec`](/src/test/groovy/example/e2e/HelloServiceSpec.groovy)

This is an end-to-end test to verify behavior of the REST API.
See also [Testing improvements in Spring Boot 1.4](https://spring.io/blog/2016/04/15/testing-improvements-in-spring-boot-1-4).

Mark as a Spring Boot test with the real web environment:

```groovy
@SpringBootTest(webEnvironment = RANDOM_PORT)
```

Make a request to the target application:

```groovy
        when:
        def entity = restTemplate.getForEntity('/hello', Hello)
```

Verify the response:

```groovy
        then:
        entity.statusCode == HttpStatus.OK
        entity.body.name == 'world'
```