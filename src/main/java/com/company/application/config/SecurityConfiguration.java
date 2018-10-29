package com.company.application.config;

import com.company.application.dao.WorkersRepository;
import com.company.application.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = WorkersRepository.class)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private WorkerService workerService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(workerService)
                .passwordEncoder(new BCryptPasswordEncoder(11));
    }

    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/admin/**").hasRole("admin")
                //.antMatchers("/formularz").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/app-login")
                    .usernameParameter("app-username")
                    .passwordParameter("app-password")
                    .permitAll()
                    .defaultSuccessUrl("/")
                .and().logout()
                    .logoutUrl("app-logout")
                    .logoutSuccessUrl("/login")
                .and().exceptionHandling()
                    .accessDeniedPage("/error");
    }
}
