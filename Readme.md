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
- We have defined enums `ApplicationUserRole` to define the user Roles, `ApplicationUserPermission` is having the enums
of the permission each role is having as per resources and finally in class `ApplicationSecurityConfig` we are having 
below code which assigns the roles to each user:
protected UserDetailsService userDetailsService() {
        
        UserDetails rajatUser = User.builder()
                .username("rajat")
                .password(passwordEncoder.encode("password"))
                .roles(STUDENT.name()) //ROLE_STUDENT (internally)
                .build();
        UserDetails rajatAdmin = User.builder()
                .username("rajatadmin")
                .password(passwordEncoder.encode("password"))
                .roles(ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager( //Using a Class that is used by Interface
                rajatUser, rajatAdmin
        );
       }}

###Role Based Authentication

- Now we need each end point to be secured by these roles and hence we are going to apply rules which will 
allow a user to access a particular endpoint.
- We are going to use  `antmatchers` for that, in the `configure` method we are going to allow users to match a
certail kind of URL.
    
        protected void configure(HttpSecurity http) throws Exception {
                http
                        .authorizeRequests()
                        /*
                        ant matchers will be used to match the patterns and whitelist them -  i.e. for all users
                         */
                        .antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
                        /*
                        Implementing role based security here
                         */
                        .antMatchers("/api/**").hasRole(STUDENT.name())
                        .anyRequest()
                        .authenticated()
                        .and()
                        .httpBasic();
                }     
- We will get 403 - Forbidden status on that

###Permission Based Authorization
It can be done in 2 ways:

- Using antmatchers, We can simply tell ant matchers what HTTP methods, a permission is applied:

                    /*
                    Permission based security is implemented here
                     */
                    .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(STUDENT_WRITE.name())
                    .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(STUDENT_WRITE.name())
                    .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(STUDENT_WRITE.name())

- We can also add Permissions nd         