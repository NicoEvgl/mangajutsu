package com.mangajutsu.webclient.config;

import javax.sql.DataSource;

import com.mangajutsu.webclient.config.handlers.CustomAccessDeniedHandler;
import com.mangajutsu.webclient.config.handlers.LoginAuthenticationFailureHandler;
import com.mangajutsu.webclient.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customUserDetailsService")
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/register/**", "/password/**")
                .permitAll()
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN_ROLE")
                .antMatchers("/anime/add-anime", "/anime/update-anime/**", "/file/**")
                .hasAnyAuthority("ADMIN_ROLE", "MEMBER_ROLE")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .formLogin(login -> {
                    try {
                        login
                                .loginPage("/login")
                                .defaultSuccessUrl("/index").failureUrl("/login?error=true")
                                .usernameParameter("username").passwordParameter("password")
                                .failureHandler(failureHandler()).and().rememberMe()
                                .tokenRepository(persistentTokenRepository())
                                .rememberMeCookieName("remember-me-mj-cookie").userDetailsService(userDetailsService)
                                .tokenValiditySeconds(2592000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
                .sessionManagement()
                .sessionFixation().migrateSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/login")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/login?invalid-session=true");
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"));
    }

    @Override
    public void configure(WebSecurity webSecurityBuilder) {
        webSecurityBuilder.ignoring()
                .antMatchers("/css/**", "/fonts/**", "/js/**", "/img/**", "/file/**/upload-file/");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) {
        authManagerBuilder.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public LoginAuthenticationFailureHandler failureHandler() {
        LoginAuthenticationFailureHandler failureHandler = new LoginAuthenticationFailureHandler();
        failureHandler.setDefaultFailureUrl("/login?error=true");
        return failureHandler;
    }
}
