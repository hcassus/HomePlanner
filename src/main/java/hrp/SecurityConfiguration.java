package hrp;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final DataSource dataSource;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void configure(WebSecurity web) {
    web
        .ignoring()
          .antMatchers("/signupuser.html")
          .antMatchers("/app.js")
          .antMatchers("/angular.min.js");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
          .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
          .authorizeRequests()
            .antMatchers("/signup").permitAll()
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            .anyRequest()
            .authenticated()
        .and()
          .formLogin()
        .and()
          .httpBasic();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .jdbcAuthentication()
          .dataSource(dataSource)
          .passwordEncoder(passwordEncoder)
        .and()
          .inMemoryAuthentication()
          .passwordEncoder(passwordEncoder)
          .withUser("admin").password("$2a$12$dSN94nIS5Pru0ZiqldTx8ODqw.YoTpt1St.EstO3PftvXuHD/W1OG")
          .authorities("ADMIN");
  }

}
