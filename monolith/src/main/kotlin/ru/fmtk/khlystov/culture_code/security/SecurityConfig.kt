package ru.fmtk.khlystov.culture_code.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.fmtk.khlystov.culture_code.security.oauth2.CustomOAuth2UserService

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun tokenAuthenticationFilter(): TokenAuthenticationFilter {
        return TokenAuthenticationFilter()
    }

    @Autowired
    private lateinit var userDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var customOAuth2UserService: CustomOAuth2UserService

    @Autowired
    private lateinit var oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler

    @Autowired
    private lateinit var oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler

    @Autowired
    private lateinit var httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository

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
