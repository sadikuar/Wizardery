package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableAutoConfiguration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.antMatchers("/", "/dashboard").permitAll()
			.antMatchers("/admin").hasAuthority("ADMIN")
			.antMatchers("/user/profile").permitAll()
			.antMatchers("/rpg").permitAll()
			.antMatchers("/user/signin").permitAll()
			.antMatchers("/user/signup").permitAll()
			.antMatchers("/creategame").hasAnyAuthority("USER", "ADMIN")
			.and()
		.formLogin() // par défaut, failure url est /user/signin?error
			.loginPage("/user/signin").permitAll()
			.usernameParameter("email")
			.defaultSuccessUrl("/user/signin/confirm")
			.and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/user/signout"));
		
		http.exceptionHandling().accessDeniedPage("/test");
		
	}
}
