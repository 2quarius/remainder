//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.security.SecurityProperties;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.oauth2.client.OAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
//import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
//import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//import org.springframework.web.filter.CompositeFilter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableOAuth2Client
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Qualifier("oauth2ClientContext")
//    @Autowired
//    OAuth2ClientContext oauth2ClientContext;
//
//    @Bean
//    @ConfigurationProperties("jaccount.client")
//    public AuthorizationCodeResourceDetails jaccount() {
//        return new AuthorizationCodeResourceDetails();
//    }
//
//    @Bean
//    @ConfigurationProperties("jaccount.resource")
//    public ResourceServerProperties githubResource() {
//        return new ResourceServerProperties();
//    }
//    /*注册一个额外的Filter：OAuth2ClientContextFilter
//     * 主要作用是重定向，当遇到需要权限的页面或URL，代码抛出异常，这时这个Filter将重定向到OAuth鉴权的地址
//     */
//    @Bean
//    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<OAuth2ClientContextFilter>();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//        return registration;
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // @formatter:off
////        http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**", "/error**").permitAll().anyRequest()
////                .authenticated().and().exceptionHandling()
////                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().logout()
////                .logoutSuccessUrl("/").permitAll().and().csrf()
////                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
//                http.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
//        // @formatter:on
//    }
//    //自定义过滤器，用于拦截oauth2第三方登录返回code的url,并根据code,clientid,clientSecret去授权服务器拿access_token
//    private OAuth2ClientAuthenticationProcessingFilter ssoFilter() {
//        //OAuth2ClientAuthenticationProcessingFilter
//        //它的构造器需要传入defaultFilterProcessesUrl，用于指定这个filter拦截哪个url。
//        //它依赖OAuth2RestTemplate来获取token
//        //还依赖ResourceServerTokenServices进行校验token
//        OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/jaccount");
//        //对rest template的封装，为获取token等提供便捷方法
//        //DefaultUserInfoRestTemplateFactory实例了OAuth2RestTemplate,这个提供了OAuth2RestTemplate
//        OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(jaccount(), oauth2ClientContext);
//        githubFilter.setRestTemplate(githubTemplate);
//        UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(), jaccount().getClientId());
//        tokenServices.setRestTemplate(githubTemplate);
//        githubFilter.setTokenServices(tokenServices);
//        return githubFilter;
//    }
//}
