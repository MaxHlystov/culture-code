package ru.fmtk.khlystov.culture_code.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    private lateinit var userDetailsService: CustomUserDetailsService

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests().antMatchers(
                        "/users/**", "recommendations/compute/**").hasAuthority(Roles.Admin.role)
                .and()

                .authorizeRequests().antMatchers("/", "/favicon.ico").permitAll()
                .and()

                .authorizeRequests().antMatchers("/login-form-processing").anonymous()
                .and()

                .authorizeRequests().antMatchers("/**").authenticated()
                .and()

                .formLogin()
                .loginProcessingUrl("/login-form-processing")
                .usernameParameter("username-for-login")
                .passwordParameter("password-for-login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login-form")
                .and()

                .rememberMe()
                .key("secret")
                .userDetailsService(userDetailsService)
                .alwaysRemember(true)
                .tokenValiditySeconds(60)
    }

}
