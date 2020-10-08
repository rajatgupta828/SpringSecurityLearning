package com.rajat.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

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
                .authorizeRequests()
                /*
                ant matchers will be used to match the patterns and whitelist them
                 */
                .antMatchers(
                        "/", "/index", "/css/*", "/js/*"
                ).permitAll()
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
                .roles("STUDENT") //ROLE_STUDENT (internally)
                .build();
        UserDetails rajatAdmin = User.builder()
                .username("rajatadmin")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager( //Using a Class that is used by Interface
                rajatUser, rajatAdmin
        );
    }
}
