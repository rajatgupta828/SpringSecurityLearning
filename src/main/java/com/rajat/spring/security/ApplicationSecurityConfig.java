package com.rajat.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.rajat.spring.security.ApplicationUserPermission.STUDENT_WRITE;
import static com.rajat.spring.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                /*
                ant matchers will be used to match the patterns and whitelist them -  i.e. for all users
                 */
                .antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
                /*
                Implementing role based security here
                 */
                .antMatchers("/api/**").hasRole(STUDENT.name())
                /*
                Permission based security is implemented here
                 */
                .antMatchers(HttpMethod.DELETE,"/management/api/**")
                .hasAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.POST,"/management/api/**")
                .hasAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.PUT,"/management/api/**")
                .hasAuthority(STUDENT_WRITE.name())
                .antMatchers(HttpMethod.GET,"/management/api/**")
                .hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    /*
    This method is overriden for providing us the user and @Bean is used so that it can be instantiated for us.
     */
    @Override
    @Bean
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
        UserDetails rajatTrainee = User.builder()
                .username("rajattrainee")
                .password(passwordEncoder.encode("password"))
                .roles(ADMINTRAINEE.name())
                .build();
        return new InMemoryUserDetailsManager( //Using a Class that is used by Interface
                rajatUser, rajatAdmin,
                rajatTrainee
        );
    }
}
