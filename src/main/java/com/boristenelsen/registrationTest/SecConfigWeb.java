package com.boristenelsen.registrationTest;

import com.boristenelsen.registrationTest.services.PortlyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecConfigWeb extends WebSecurityConfigurerAdapter {

    @Autowired
    PortlyUserDetailsService portlyUserDetailsService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(portlyUserDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**").hasAnyRole("ANONYMOUS,USER", "ADMIN")
                .antMatchers("/login").hasAnyRole("ANONYMOUS", "USER", "ADMIN")
                .antMatchers("/registration").hasAnyRole("ANONYMOUS", "USER", "ADMIN")
                .antMatchers("/home").hasAnyRole("USER", "ADMIN")
                .antMatchers("/home/").hasAnyRole("USER", "ADMIN")
                .antMatchers("/home/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/admin/").hasAnyRole("ADMIN")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/home");

        /*Logs out and clears the security Context*/
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
