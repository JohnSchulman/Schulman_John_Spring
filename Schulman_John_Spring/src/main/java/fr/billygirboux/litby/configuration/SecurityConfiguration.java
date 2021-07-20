package fr.billygirboux.litby.configuration;
// SecurityConfiguration.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.sql.DataSource;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	 auth
         .jdbcAuthentication()
             .usersByUsernameQuery(
                "select username, password, 1 from users where username=?")
             .authoritiesByUsernameQuery(
                "select u.username, r.roleName from users u inner join roles r on (u.id=r.userId) where u.username=?")
             .dataSource(dataSource)
             .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasAuthority("ROLE_ADMIN")
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.GET, "/users/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.PATCH, "/users/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers(HttpMethod.GET, "/links").permitAll()
                .antMatchers(HttpMethod.POST, "/links").permitAll()
                .antMatchers(HttpMethod.DELETE, "/links/*").hasAuthority("ROLE_ADMIN")

                .anyRequest().denyAll()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
	}

}
