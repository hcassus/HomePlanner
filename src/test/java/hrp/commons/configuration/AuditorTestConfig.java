package hrp.commons.configuration;

import static org.mockito.Mockito.when;

import hrp.auth.persistence.AuditorAwareImpl;
import java.util.Optional;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditorTestConfig {

  private AuditorAwareImpl auditorAware;

  private final String VALID_USERNAME = System.getenv("VALID_USERNAME");

  @Bean
  AuditorAware<String> auditorProvider() {
    auditorAware = Mockito.mock(AuditorAwareImpl.class);
    when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of(VALID_USERNAME));
    return auditorAware;
  }
}
