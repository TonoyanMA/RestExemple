package com.courseexemple.sarafan.config;

import com.courseexemple.sarafan.domen.User;
import com.courseexemple.sarafan.repo.UserDetailesRepo;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/js/**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }


    @Bean
    public PrincipalExtractor principalExtractor (UserDetailesRepo userDetailesRepo){
      return map -> {
          String id = (String) map.get("sub");
          User user = userDetailesRepo.findById(id).orElseGet(() -> {
              User newUser = new User();
              newUser.setId(id);
              newUser.setName((String) map.get("name"));
              newUser.setEmail((String) map.get("email"));
              newUser.setGender((String) map.get("gender"));
              newUser.setLocale((String) map.get("local"));
              newUser.setUserpic((String) map.get("picture"));
              return newUser;
          });

          user.setLastVisit(LocalDateTime.now());

          return userDetailesRepo.save(user);
      };
    }
}
