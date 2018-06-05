package hrp;

import hrp.auth.persistence.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditingConfig {

  @Bean
  AuditorAware<String> auditorProvider() {
    return new AuditorAwareImpl();
  }

}
