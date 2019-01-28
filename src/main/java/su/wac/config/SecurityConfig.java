package su.wac.config;

import su.wac.security.CourseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private CourseDetailService courseDetailService;

    @Autowired
    public void setCourseDetailService(CourseDetailService courseDetailService){
        this.courseDetailService = courseDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/index.html","/course/**","/student/**").hasRole("ADMIN").anyRequest().authenticated()
                .antMatchers("/swagger-ui.html").permitAll().anyRequest().permitAll()
                .antMatchers(HttpMethod.POST,"/signin").permitAll()
                .and()
                .formLogin().loginPage("/").permitAll()
                .loginProcessingUrl("/signin")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/index.html",true).and()
                .logout().logoutSuccessUrl("/")
                .permitAll()
                .invalidateHttpSession(true);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/h2console/**");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(courseDetailService).passwordEncoder(passwordEncoder());
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(){};
    }
}