package com.fundfun.fundfund.config.auth;

import com.fundfun.fundfund.service.user.CustomUserDetailService;
import com.fundfun.fundfund.service.user.OAuth2UserService;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@RequiredArgsConstructor
@EnableWebSecurity//시큐리티 필터
@EnableGlobalMethodSecurity(prePostEnabled = true)// 특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 권한 및 인증을 미리 체크하겠다는 설정을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailService customUserDetailService;
    private final OAuth2UserService oAuth2UserService;
    private final AuthSuccessHandler authSuccessHandler;
//    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder encryptPassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(encryptPassword());

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
		 csrf 토큰 활성화시 사용
		 쿠키를 생성할 때 HttpOnly 태그를 사용하면 클라이언트 스크립트가 보호된 쿠키에 액세스하는 위험을 줄일 수 있으므로 쿠키의 보안을 강화할 수 있다.
		*/
        //http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        http.csrf().disable()    // csrf 토큰을 비활성화
                .authorizeRequests() // 요청 URL에 따라 접근 권한을 설정
//                .antMatchers().authenticated()//2022-06-29_yeoooo: antPatter에 따라 인증이 필요한 경로
//                .antMatchers("/**", "/login/**", "/js/**", "/css/**", "/image/**")
//                .permitAll() // 해당 경로들은 접근을 허용
//                .anyRequest() // 다른 모든 요청은
                .antMatchers("/test")
                .authenticated() // 인증된 유저만 접근을 허용
                .and()
                .headers()
                .cacheControl()
                .disable()
                .and()
                .formLogin() // 로그인 폼은
//                .usernameParameter("inputEmail")// 이곳에는 login page의 input tag id 를 넣어야 한다.
//                .passwordParameter("inputPassword") //이곳에는 login page의 input tag id 를 넣어야 한다.
                .loginPage("/login") // 해당 주소로 로그인 페이지를 호출한다.
                .loginProcessingUrl("/login/action") // 해당 URL로 요청이 오면 스프링 시큐리티가 가로채서 로그인처리를 한다. -> loadUserByName
                .defaultSuccessUrl("/")
                .and()
                //JWT
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL
                .logoutSuccessUrl("/") // 성공시 리턴 URL
                .invalidateHttpSession(true) // 인증정보를 지우하고 세션을 무효화
//                .deleteCookies("JSESSIONID") // JSESSIONID 쿠키 삭제
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1) // 세션 최대 허용 수 1, -1인 경우 무제한 세션 허용
                .maxSessionsPreventsLogin(false) // true면 중복 로그인을 막고, false면 이전 로그인의 세션을 해제
                .expiredUrl("/login?error=true&exception=Have been attempted to login from a new place. or session expired")  // 세션이 만료된 경우 이동 할 페이지를 지정
                .and()
                .and().rememberMe() // 로그인 유지
                .alwaysRemember(false) // 항상 기억할 것인지 여부` /
                .tokenValiditySeconds(43200) //- in seconds, 12시간 유지
                .rememberMeParameter("remember-me")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .successHandler(authSuccessHandler)
                .userInfoEndpoint()
                // 소셜로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
                .userService(oAuth2UserService) // 리소스 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시
        ;


    }
}