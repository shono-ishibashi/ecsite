package com.ecsite.demo.config;


import com.ecsite.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .authorizeRequests()
                //指定したパターンごとに制限をかける
                .antMatchers(
                        "/user/login_form"
                        ,"/user/register"
                        ,"/user/register_form"
                        ,"/list"
                        ,"/cart/show"
                        ,"/detail"
                        ,"/cart/add"
                        ,"/cart/remove"
                        ,"/search"
                        ,"/static/js/**"
                ).permitAll()
                .anyRequest().authenticated()

                //アクセスの許可
                .and()

                .formLogin()
                .loginPage("/user/login_form")

                .loginProcessingUrl("/executeLogin")
                .defaultSuccessUrl("/list")

                .usernameParameter("email")
                .passwordParameter("password")

                .and()

                .logout()
                //  ログアウト時の遷移先URL
                .logoutSuccessUrl("/list");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    @Autowired
    void configureAuthenticationManager(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}