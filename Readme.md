#Spring Security Addition Steps

##We have created a API `StudentController.java` which we are going to secure.

####Step 1  : Add the dependency for Spring Security

    <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
        <version>2.3.4.RELEASE</version>
    </dependency>

Simply adding this will add form based security. The default user
will be User and Password will be generated on the Console for this.

##Basic Auth
It is simplest authentication. We have a client that has to access some request.
Client sends a request, with Basic auth we will get `401` response code.
We need to specify `username` and `password` in th header of base 64.
Server then validates and gives correct response.
It is good when we are accessing external applications/Apis.

##Implementation of Basic Auth
- To implement Basic security we have used `ApplicationSecurityConfig` under `Security` Package.
- It is annotated with `@EnableWebSecurity` which tells us that all the security is going to be written
here.
- We will also extend `WebSecurityConfigurerAdapter` class to overrider methods.
- `configure` this method will be overriden for HTTP security, The statements below

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
        }
    These statements tell that any request should be authorized, authenticated and basic auth is used.
- With basic Auth you can not logout as form based security and username and password has to be
sent with every single request.
- When calling from POstman tool etc, navigate to Authorization tab and provide the ID 
and password to get successfull result, else it will end up in 401.


##Whitelisting the URLs

- Some URLs should be accessible to public, that is called as whitelisting the URLs.
- We can implement ant matchers , to match the Url namings and mark them as whitelisted.
- Apart from the URLs listed, else everything will be needing password.

##Creating Usernames and Passwords
- Fields would be Username, Passwords(That would be encrypted), Role(which will define authentication level).

###In memory User
`UserDetailsService` method present in `ApplicationSecurityConfig` will be used to create In memory user.
###Password Encrypting with BCRYPT
- Class `PasswordConfig` under the `security` package is written to configure the password and it's encryption.
- We need to encrypt the password else the Spring boot security will throw the exception.
- We will be using `encode` method from `PasswordEncoder` to encode the password and Springboot uses this
internally to encode the password.

###Roles and Permission
- There are different roles that can be assigned to a user, and then they have different permision
based on the roles.
- Under the `UserDetailsService` method present in `ApplicationSecurityConfig` we are going to create a User with admin 
role.
- We can attach permission to the roles, we can have multiple roles for a particular user.
- We are using Enums to define roles in thye applications. In security package we have 
`ApplicationUserRole` 









    

