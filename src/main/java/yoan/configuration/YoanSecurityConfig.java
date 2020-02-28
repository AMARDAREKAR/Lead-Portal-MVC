package yoan.configuration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class YoanSecurityConfig extends WebSecurityConfigurerAdapter {  
 	
	  @Autowired
	  private UserDetailsService userDetailsService;
	  
	  @Bean
	  public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  };
	  
	  @Override
	  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	  }
	  	
	  @Override
	  protected void configure(HttpSecurity http) throws Exception {
		  http
		  	.cors()
		  	.and()
		  	.authorizeRequests()
			.antMatchers("/login**").permitAll()
			.antMatchers("/resources/images/yoan-logo.png").permitAll()		
			.antMatchers("/resources/javascripts/rest.js").permitAll()
			.antMatchers("/resources/javascripts/validation.js").permitAll()
			.antMatchers("/bdm**").access("hasRole('BDM')")
			.antMatchers("/agent**").access("hasRole('AGENT')")
		    .antMatchers("/tl**").access("hasRole('TEAM LEADER')")
		    .anyRequest().authenticated()
		    .and()
	        .httpBasic()
			.and()
		    .formLogin().loginPage("/login").loginProcessingUrl("/loginAction").permitAll()
		    .and()
		    .logout().logoutUrl("/logout**").logoutSuccessUrl("/login").permitAll()
		    .and()
		    .exceptionHandling().accessDeniedPage("/accessdenied")
		  	.and()
		  	.rememberMe()  
		    .key("rem-me-key")  
		    .rememberMeParameter("remember") // it is name of checkbox at login page  
		    .rememberMeCookieName("rememberlogin") // it is name of the cookie  
		    .tokenValiditySeconds(100) // remember for number of seconds  
		    .and()  	
		  	.csrf().disable();
	  }
	  
	  @Bean
	  CorsConfigurationSource corsConfigurationSource() 
	  {
		  	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		  	CorsConfiguration configuration = new CorsConfiguration();
		  	configuration.setAllowCredentials(true);
		  	configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		  	configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS"));
		  	configuration.setAllowedHeaders(Collections.singletonList("*"));
		  	source.registerCorsConfiguration("/**", configuration);
		  	return source;
	  }	  
}