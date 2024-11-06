## Exercise BCI

### Set up

Must be set Java 11 as default, you can use [sdkman](https://sdkman.io/) to handle it

Once you installed sdkman, must run 

`sdk install java 11.0.25-amzn` 

after is installed, run

`sdk usage java 11.0.25-amzn`

to verify it you can run `java --version`

```
openjdk 11.0.25 2024-10-15 LTS
OpenJDK Runtime Environment Corretto-11.0.25.9.1 (build 11.0.25+9-LTS)
OpenJDK 64-Bit Server VM Corretto-11.0.25.9.1 (build 11.0.25+9-LTS, mixed mode)
```

Once is java 11 installed, you must install gradle 7.4

`sdk install gradle 7.4`

when finish it, you can verify running `gradle --version`

### Swagger

[Here](http://localhost:8080/api/swagger-ui.html) you can explore API

### Diagrams

#### Component diagram
For more details please go to [component folder](./docs/services/component.md)

#### Sequence diagram
[Sign-up service](./docs/services/signup_flow.md)

[Login service](./docs/services/login_flow.md)

