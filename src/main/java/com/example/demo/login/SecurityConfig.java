package com.example.demo.login;

import com.example.demo.login.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {//extends WebSecurityConfiguration{//WebSecurityConfigurerAdapter {
    private LoginService loginService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

  /*  @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**");//antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }
*/

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // antMatchers 부분도 deprecated 되어 requestMatchers로 대체
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }



/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 페이지 권한 설정
                .requestMatchers("/admin/**").hasRole("ADMIN") //antMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers(("/user/myinfo").hasRole("MEMBER")) //antMatchers("/user/myinfo").hasRole("MEMBER")
                .antMatchers("/**").equals()
                .and() // 로그인 설정
                .formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/user/login/result")
                .equals()
                .and() // 로그아웃 설정
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/logout/result")
                .invalidateHttpSession(true)
                .and()
                // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/user/denied");
    }
 */

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
            auth
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/user/myinfo").hasRole("MEMBER")
                    .requestMatchers("/**").permitAll()
        );

        http.formLogin(form ->
                form
                        .loginPage("/user/login")
                        .defaultSuccessUrl("/user/login/result")
                        .permitAll()
        );

        http.logout(form ->
                form
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                        .logoutSuccessUrl("/user/logout/result")
                        .invalidateHttpSession(true)
        );

        http.exceptionHandling(form ->
                form.accessDeniedPage("/user/denied")
        );

            return http.build();
    }

    //@Bean
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }

}
