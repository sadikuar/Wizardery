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

import com.example.demo.utils.RoleEnum;
import com.example.demo.utils.Routes;

@Configuration
@EnableAutoConfiguration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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
		String admin = RoleEnum.ADMIN.toString();
		
		http
		.authorizeRequests()
			.antMatchers(Routes.DASHBOARD, "/dashboard").permitAll()
			.antMatchers(Routes.RPG_DETAILS).permitAll()
			.antMatchers(Routes.SCENARIO_DETAILS).permitAll()
			.antMatchers(Routes.SIGNIN).permitAll()
			.antMatchers(Routes.SIGNUP).permitAll()
			.antMatchers(Routes.USER_DETAILS).authenticated()
			.antMatchers(Routes.USER_DETAILS + "/*/update").authenticated()
			.antMatchers(Routes.RPG_CREATE).authenticated()
			.antMatchers(Routes.RPG_DETAILS + "/*/addToFavorite").authenticated()
			.antMatchers(Routes.SCENARIO_CREATE).authenticated()
			.antMatchers(Routes.SCENARIO_DETAILS + "/*/addToFavorite").authenticated()
			.antMatchers(Routes.USER_DELETE).authenticated()
			.antMatchers(Routes.ADMIN).hasAuthority(admin)
			.antMatchers(Routes.RPG_DETAILS + "/*/forceDelete").hasAuthority(admin)
			.antMatchers(Routes.SCENARIO_DETAILS + "/*/forceDelete").hasAuthority(admin)
			.and()
		.formLogin() // par défaut, failure url est /user/signin?error
			.loginPage(Routes.SIGNIN).permitAll()
			.usernameParameter("email")
			.defaultSuccessUrl(Routes.SIGNIN_CONFIRM, true)
			.and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher(Routes.SIGNOUT));
		
		http.exceptionHandling().accessDeniedPage(Routes.ERROR_FORBIDDEN);
	}
}
