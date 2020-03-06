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
		String user = RoleEnum.USER.toString();

		http
		.authorizeRequests()
			.antMatchers(Routes.DASHBOARD, "/dashboard").permitAll()
			.antMatchers("/admin").hasAuthority(admin)
			.antMatchers(Routes.PROFILE).permitAll()
			.antMatchers("/rpg").permitAll()
			.antMatchers(Routes.SIGNIN).permitAll()
			.antMatchers(Routes.SIGNUP).permitAll()
			.antMatchers(Routes.RPG_CREATE).hasAnyAuthority(user, admin)
			.and()
		.formLogin() // par défaut, failure url est /user/signin?error
			.loginPage(Routes.SIGNIN).permitAll()
			.usernameParameter("email")
			.defaultSuccessUrl(Routes.SIGNIN_CONFIRM)
			.and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/user/signout"));
		
		http.exceptionHandling().accessDeniedPage("/test");
	}
}
