package it.polimi.inginf.childcaretech.security;

import it.polimi.inginf.childcaretech.data.Staff;
import it.polimi.inginf.childcaretech.repositories.StaffRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Declare a PasswordEncoder bean, which we’ll use both when creating new users and when authenticating users at login.
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(StaffRepository userRepo) { //FIXME
        return username -> {
            Staff user = userRepo.findByUsername(username);
            System.out.println(user);
            if (user != null) {
                return user;
            }
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("/","/anagrafica").hasRole("USER") //Don't include the ROLE_ prefix on roles passed to hasRole(); it will be assumed by hasRole()
                .antMatchers("/register").permitAll()

                .and()
                .formLogin()//Starts configuring custom login form
                .loginPage("/login") //where custom login page will be provided
                .defaultSuccessUrl("/")//when a user successfully log in after directly going to the login page, he would be directed to the / page. But also go to the page that they were
                                       //navigating to when Spring Security determined that they needed to log in.


                .and()
                .logout()
                .logoutSuccessUrl("/login") //when a user successfully log in after directly going to the login page, he would be directed to the / page. But also go to the page that they were
                                       //navigating to when Spring Security determined that they needed to log in.

                .and()
                .build();
    }



}
