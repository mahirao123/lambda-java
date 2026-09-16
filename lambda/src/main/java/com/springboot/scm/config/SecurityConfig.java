package com.springboot.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.springboot.scm.employeeImpl.EmployeeSecurityUserDetailService;
import com.springboot.scm.impl.SecurityCostomUserDetailService;

@Configuration
public class SecurityConfig {

    @Autowired
    private EmployeeSecurityUserDetailService employeeDetailService;

    @Autowired
    private SecurityCostomUserDetailService userDetailService;

    @Autowired
    private OAuthAthenticationSuccessHandler handler;

    @Autowired
    private RoleBasedAuthenticationSuccessHandler successHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==============================
    // Employee Authentication Provider
    // ==============================
    @Bean
    AuthenticationProvider employeeAuthenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(
                employeeDetailService);

        provider.setPasswordEncoder(
                passwordEncoder);

        return provider;
    }

    // ==============================
    // User Authentication Provider
    // ==============================
    @Bean
    AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(
                userDetailService);

        provider.setPasswordEncoder(
                passwordEncoder);

        return provider;
    }

    // ==============================
    // ADMIN SECURITY
    // ==============================
    @Bean
    @Order(1)
    SecurityFilterChain adminSecurity(
            HttpSecurity http)
            throws Exception {

        http
        .csrf(csrf -> csrf
                .csrfTokenRepository(
                    CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
            )
            .securityMatcher("/admin/**")

            .authorizeHttpRequests(auth -> {

                auth.requestMatchers(
                        "/admin/login")
                        .permitAll();

                auth.anyRequest()
                        .hasRole("ADMIN");
            })

            .formLogin(login -> {

                login.loginPage(
                        "/admin/login");

                login.loginProcessingUrl(
                        "/admin/authenticate");

                login.successHandler(
                        successHandler);

                login.failureUrl(
                        "/admin/login?error=true");

                login.usernameParameter(
                        "email");

                login.passwordParameter(
                        "password");
            })

            .logout(logout -> {

                logout.logoutUrl(
                        "/admin/logout");

                logout.logoutSuccessUrl(
                        "/admin/login?logout=true");
            })

            .csrf(
                    AbstractHttpConfigurer::disable);

        return http.build();
    }
    
    

    // ==============================
    // MANAGER SECURITY
    // ==============================
    @Bean
    @Order(2)
    SecurityFilterChain managerSecurity(
            HttpSecurity http)
            throws Exception {

        http
        .csrf(csrf -> csrf
                .csrfTokenRepository(
                    CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
            )
            .securityMatcher(
                    "/manager/**")

            .authorizeHttpRequests(auth -> {

                auth.requestMatchers(
                        "/employee/login")
                        .permitAll();

                auth.anyRequest()
                        .hasRole("MANAGER");
            })

            .formLogin(login -> {

                login.loginPage(
                        "/employee/login");

                login.loginProcessingUrl(
                        "/employee/authenticate");

                login.successHandler(
                        successHandler);

                login.failureUrl(
                        "/employee/login?error=true");

                login.usernameParameter(
                        "email");

                login.passwordParameter(
                        "password");
            })

            .logout(logout -> {

                logout.logoutUrl(
                        "/employee/logout");

                logout.logoutSuccessUrl(
                        "/employee/login?logout=true");
            })

            .csrf(
                    AbstractHttpConfigurer::disable);

        return http.build();
    }
    
    // ==============================
    //  EDITORS SECURITY
    // ==============================
    @Bean
    @Order(3)
    SecurityFilterChain editorSecurity(
            HttpSecurity http)
            throws Exception {

        http
        .csrf(csrf -> csrf
                .csrfTokenRepository(
                    CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
            )
            .securityMatcher(
                    "/editor/**")

            .authorizeHttpRequests(auth -> {

                auth.requestMatchers(
                        "/employee/login")
                        .permitAll();

                auth.anyRequest()
                        .hasAnyRole("EDITOR","MANAGER","HR","ADMIN");
            })

            .formLogin(login -> {

                login.loginPage(
                        "/employee/login");

                login.loginProcessingUrl(
                        "/employee/authenticate");

                login.successHandler(
                        successHandler);

                login.failureUrl(
                        "/employee/login?error=true");

                login.usernameParameter(
                        "email");

                login.passwordParameter(
                        "password");
            })

            .logout(logout -> {

                logout.logoutUrl(
                        "/employee/logout");

                logout.logoutSuccessUrl(
                        "/employee/login?logout=true");
            })

            .csrf(
                    AbstractHttpConfigurer::disable);

        return http.build();
    }
    
    // ==============================
    //  PODCASTER AND ANCHOR SECURITY
    // ==============================
    @Bean
    @Order(4)
    SecurityFilterChain productionTeamSecurity(
            HttpSecurity http)
            throws Exception {

        http
        .csrf(csrf -> csrf
                .csrfTokenRepository(
                    CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
            )
            .securityMatcher(
                    "/production/**")

            .authorizeHttpRequests(auth -> {

                auth.requestMatchers(
                        "/employee/login")
                        .permitAll();

                auth.anyRequest()
                        .hasAnyRole("PODCASTER","ANCHOR","MANAGER","HR","ADMIN");
            })

            .formLogin(login -> {

                login.loginPage(
                        "/employee/login");

                login.loginProcessingUrl(
                        "/employee/authenticate");

                login.successHandler(
                        successHandler);

                login.failureUrl(
                        "/employee/login?error=true");

                login.usernameParameter(
                        "email");

                login.passwordParameter(
                        "password");
            })

            .logout(logout -> {

                logout.logoutUrl(
                        "/employee/logout");

                logout.logoutSuccessUrl(
                        "/employee/login?logout=true");
            })

            .csrf(
                    AbstractHttpConfigurer::disable);

        return http.build();
    }
    
    // ==============================
    //  FINANCE SECURITY
    // ==============================
    @Bean
    @Order(5)
    SecurityFilterChain financeSecurity(
            HttpSecurity http)
            throws Exception {

        http
        .csrf(csrf -> csrf
                .csrfTokenRepository(
                    CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
            )
            .securityMatcher(
                    "/finance/**")

            .authorizeHttpRequests(auth -> {

                auth.requestMatchers(
                        "/employee/login")
                        .permitAll();

                auth.anyRequest()
                        .hasAnyRole("FINANCE","ADMIN");
            })

            .formLogin(login -> {

                login.loginPage(
                        "/employee/login");

                login.loginProcessingUrl(
                        "/employee/authenticate");

                login.successHandler(
                        successHandler);

                login.failureUrl(
                        "/employee/login?error=true");

                login.usernameParameter(
                        "email");

                login.passwordParameter(
                        "password");
            })

            .logout(logout -> {

                logout.logoutUrl(
                        "/employee/logout");

                logout.logoutSuccessUrl(
                        "/employee/login?logout=true");
            })

            .csrf(
                    AbstractHttpConfigurer::disable);

        return http.build();
    }
    
    // ==============================
    //  FINANCE SECURITY
    // ==============================
    @Bean
    @Order(6)
    SecurityFilterChain socialMeadiaSecurity(
            HttpSecurity http)
            throws Exception {

        http
        .csrf(csrf -> csrf
                .csrfTokenRepository(
                    CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
            )
            .securityMatcher(
                    "/socialMeadia/**")

            .authorizeHttpRequests(auth -> {

                auth.requestMatchers(
                        "/employee/login")
                        .permitAll();

                auth.anyRequest()
                        .hasAnyRole("SOCIAL_MEADIA","ADMIN");
            })

            .formLogin(login -> {

                login.loginPage(
                        "/employee/login");

                login.loginProcessingUrl(
                        "/employee/authenticate");

                login.successHandler(
                        successHandler);

                login.failureUrl(
                        "/employee/login?error=true");

                login.usernameParameter(
                        "email");

                login.passwordParameter(
                        "password");
            })

            .logout(logout -> {

                logout.logoutUrl(
                        "/employee/logout");

                logout.logoutSuccessUrl(
                        "/employee/login?logout=true");
            })

            .csrf(
                    AbstractHttpConfigurer::disable);

        return http.build();
    }

    // ==============================
    // EMPLOYEE SECURITY
    // ==============================
    @Bean
    @Order(7)
    SecurityFilterChain employeeSecurity(
            HttpSecurity http)
            throws Exception {

        http
        .csrf(csrf -> csrf
                .csrfTokenRepository(
                    CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
            )
            .securityMatcher("/employee/**")

            .authenticationProvider(
                    employeeAuthenticationProvider())

            .authorizeHttpRequests(auth -> {

                auth.requestMatchers(
                        "/employee/login",
                        "/employee/signup",
                        "/employee/do-employeeRegister")
                        .permitAll();

                auth.anyRequest()
                        .hasAnyRole(
                                
                                "HR",
                                "PODCASTER",
                                "SOCIAL_MEDIA",
                                "EDITOR",
                                "OTHER",
                                "SALES",
                                "FINANCE",
                                "MANAGER",                             
                                "CUSTOMER_CARE");
            })

            .formLogin(login -> {

                // FIXED LOGIN URL
                login.loginPage(
                        "/employee/login");

                login.loginProcessingUrl(
                        "/employee/authenticate");

                login.successHandler(
                        successHandler);

                login.failureUrl(
                        "/employee/login?error=true");

                login.usernameParameter(
                        "email");

                login.passwordParameter(
                        "password");
            })

            .logout(logout -> {

                logout.logoutUrl(
                        "/employee/logout");

                logout.logoutSuccessUrl(
                        "/employee/login?logout=true");
            })

            .csrf(
                    AbstractHttpConfigurer::disable);

        return http.build();
    }

    // ==============================
    // COMMON USER SECURITY
    // ==============================
    
    // ==============================
    // ADMIN SECURITY
    // ==============================
    @Bean
    @Order(8)
    SecurityFilterChain hrSecurity(
            HttpSecurity http)
            throws Exception {

        http
        .csrf(csrf -> csrf
                .csrfTokenRepository(
                    CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
            )
            .securityMatcher("/hr/**")

            .authorizeHttpRequests(auth -> {

                auth.requestMatchers(
                        "/employee/login")
                        .permitAll();

                auth.anyRequest()
                        .hasAnyRole("HR","MANAGER","ADMIN");
            })

            .formLogin(login -> {

                login.loginPage(
                        "/employee/login");

                login.loginProcessingUrl(
                        "/employee/authenticate");

                login.successHandler(
                        successHandler);

                login.failureUrl(
                        "/employee/login?error=true");

                login.usernameParameter(
                        "email");

                login.passwordParameter(
                        "password");
            })

            .logout(logout -> {

                logout.logoutUrl(
                        "/employee/logout");

                logout.logoutSuccessUrl(
                        "/employee/login?logout=true");
            })

            .csrf(
                    AbstractHttpConfigurer::disable);

        return http.build();
    }

    
@Bean
@Order(9)
SecurityFilterChain commonSecurity(
        HttpSecurity http)
        throws Exception {

    http

        // ADD THIS
    .csrf(csrf -> csrf
            .csrfTokenRepository(
                CookieCsrfTokenRepository.withHttpOnlyFalse()
            )
        )
        .authenticationProvider(
                authenticationProvider())

        .authorizeHttpRequests(auth -> {

            auth.requestMatchers(
                    "/",
                    "/home",
                    "/login",
                    "/signup",
                    "/register",
                    "/employee/signup",
                    "/employeeRegister",
                    "/do-register",
                    "/css/**",
                    "/js/**",
                    "/images/**")
                    .permitAll();

            auth.requestMatchers(
                    "/user/**")
                    .hasRole("USER");

            auth.anyRequest()
                    .permitAll();
        });

    http.formLogin(login -> {

        login.loginPage("/login");

        login.loginProcessingUrl(
                "/authenticate");

        login.successHandler(
                successHandler);

        login.failureUrl(
                "/login?error=true");

        login.usernameParameter(
                "email");

        login.passwordParameter(
                "password");
    });

    // OAuth Login
    http.oauth2Login(oauth -> {

        oauth.loginPage("/login");

        oauth.userInfoEndpoint(userInfo ->
                userInfo.userService(
                        customOAuth2UserService));

        oauth.successHandler(handler);
    });

    // Logout
    http.logout(logout -> {

        logout.logoutUrl(
                "/do-logout");

        logout.logoutSuccessUrl(
                "/login?logout=true");
    });

    http.csrf(
            AbstractHttpConfigurer::disable);

    return http.build();
}
}