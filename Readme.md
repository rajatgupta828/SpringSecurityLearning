#Spring Security Addition Steps

## We have created a API `StudentController.java` which we are going to secure.

####Step 1  : Add the dependency for Spring Security

    <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
        <version>2.3.4.RELEASE</version>
    </dependency>

Simply adding this will add form based security. The default user
will be User and Password will be generated on the Console for this.


    

