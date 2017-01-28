/**
* The SecurityConfiguration program initilized security configuration for  
* Parts Hub Application
*
* @author  Nuthan Kumar
* @version 1.0
* @since   6/27/2016
* ***************************************************************************
* History : 6/27/2016 Created with default configure method with csrf filters
* 			6/28/2016 added Rest Auth success and error handlers
* 			6/29/2016  added remind me feature
*  **************************************************************************
*  TODO : 1) update/replace configureGlobal with actula authentication
*  **************************************************************************
*/

package com.vilma.spring.configuration;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.vilma.spring.security.RememberMeServices;
import com.vilma.spring.security.RestUnauthorizedEntryPoint;


@Configuration
@EnableWebSecurity

@ComponentScan(basePackages = {"com.vilma.spring.security"})
@EnableAutoConfiguration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    public static final String REMEMBER_ME_KEY = "rememberme_key";
    
	
    public SecurityConfiguration() {
        super();
        logger.info("loading SecurityConfig ................................................ ");
    }
    
    
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler restAccessDeniedHandler;

//    @Autowired
  //  private RememberMeServices rememberMeServices;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    
    
	 @Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers("/resources/**", "/index.html", "/login.html",
	                "/partials/**", "/template/**", "/", "/error/**","/resources/img/**");
	    }
	 
	 
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .headers().disable()
	            .csrf().disable()
	            .authorizeRequests()
	                .antMatchers("/failure").permitAll()
	                .antMatchers("/users/**").hasAnyAuthority("admin")
	                .anyRequest().authenticated()
	                .and()
	            .exceptionHandling()
	                .authenticationEntryPoint(restAuthenticationEntryPoint)
	                .accessDeniedHandler(restAccessDeniedHandler)
	                .and()
	            .formLogin()
	                .loginProcessingUrl("/authenticate")
	                .successHandler(restAuthenticationSuccessHandler)
	                .failureHandler(restAuthenticationFailureHandler)
	                .usernameParameter("username")
	                .passwordParameter("password")
	                .permitAll()
	                .and()
	            .logout()
	                .logoutUrl("/logout")
	                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
	                .deleteCookies("JSESSIONID")
	                .permitAll()
	                .and();
	               // .httpBasic().disable();
	         //   .rememberMe()
	           //     .rememberMeServices(rememberMeServices)
	             //   .key(REMEMBER_ME_KEY)
	               // .and();
	    }
	
	@Autowired
    private AuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler restAuthenticationFailureHandler;
    	
}
