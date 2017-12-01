package com.khoubyari.example.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@EnableWebSecurity
@Profile("!insecure")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Value(value = "${auth0.apiAudience}")
    private String audience;
    @Value(value = "${auth0.issuer}")
    private String issuer;
    
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JwtWebSecurityConfigurer
			.forRS256(audience, issuer)
			.configure(http)
			.authorizeRequests()
            .antMatchers(HttpMethod.GET, "/example/v1/login").permitAll()
            .antMatchers(HttpMethod.GET, "/example/v1/hotels/**").hasAuthority("read:hotels")
            .antMatchers(HttpMethod.POST, "/example/v1/hotels/**").hasAuthority("create:hotels	")
            .antMatchers(HttpMethod.PUT, "/example/v1/hotels/**").hasAuthority("update:hotels")
            .antMatchers(HttpMethod.DELETE, "/example/v1/hotels/**").hasAuthority("delete:hotels")
            .anyRequest().authenticated();
	}
	
}
