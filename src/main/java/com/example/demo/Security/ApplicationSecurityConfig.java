package com.example.demo.Security;

import com.example.demo.Auth.ApplicationUserServices;
import com.example.demo.Jwt.JwtConfig;
import com.example.demo.Jwt.JwtSecretKey;
import com.example.demo.Jwt.JwtTokenVerifier;
import com.example.demo.Jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import java.security.Key;

import static com.example.demo.Security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserServices applicationUserServices;
    private final JwtConfig jwtConfig;
    private final JwtSecretKey secretKey;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserServices applicationUserServices, JwtConfig jwtConfig, JwtSecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserServices = applicationUserServices;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                .addFilterAfter(new JwtTokenVerifier((SecretKey) secretKey, jwtConfig),JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
//                .antMatchers("/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINNE.name())
//                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .anyRequest()
                .authenticated();



//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .defaultSuccessUrl("/courses", true)
//                .passwordParameter("password")
//                .usernameParameter("username")
//                .and()
//                .rememberMe()
//                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(123))
//                .key("something more secure")
//                .rememberMeParameter("remember-me")
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//                .logoutUrl("/logout")
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSEXXIONID", "Rembember-me")
//                .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider =new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserServices);
        return provider;
    }
}

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails bibekUser = User.builder()
//                .username("bibek")
//                .password(passwordEncoder.encode("12345"))
////                .roles(STUDENT.name())
//                .authorities(STUDENT.getGrantedAuthorities() )
//                .build();
//
//        UserDetails abcUser = User.builder()
//                .username("bibek1")
//                .password(passwordEncoder.encode("bibek123"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//
//        UserDetails tomUSer = User.builder()
//                .username("tom")
//                .password(passwordEncoder.encode("tom123"))
////                .roles(ADMINTRAINNE.name())
//                .authorities(ADMINTRAINNE.getGrantedAuthorities())
//                .build();
//
//        return new InMemoryUserDetailsManager(
//                bibekUser,
//                abcUser,
//                tomUSer
//        );
//
//    }

//
//}



