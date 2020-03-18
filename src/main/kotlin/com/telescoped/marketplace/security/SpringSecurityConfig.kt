package com.telescoped.marketplace.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
class SpringSecurityConfig(private val datasource: DataSource) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.jdbcAuthentication()
                ?.dataSource(datasource)
                ?.passwordEncoder(BCryptPasswordEncoder())
                ?.usersByUsernameQuery("select username,password,enabled from users where username=?")
                ?.authoritiesByUsernameQuery("select username, 'ROLE_USER' from users where username=?")
    }

    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()
                ?.antMatchers("/css/**", "/images/**")?.permitAll()
                ?.anyRequest()
                ?.authenticated()
                ?.and()
                ?.formLogin()
                ?.loginPage("/login")
                ?.loginProcessingUrl("/authenticate")
                ?.permitAll()
                ?.and()
                ?.logout()
                ?.logoutSuccessUrl("/login")
                ?.permitAll()
                ?.logoutUrl("/logout")
                ?.and()
                ?.exceptionHandling()
                ?.accessDeniedPage("/login")
    }
}