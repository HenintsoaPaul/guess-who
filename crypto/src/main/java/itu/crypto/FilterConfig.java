package itu.crypto;

import itu.crypto.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final RestTemplate restTemplate = new RestTemplate();
    private final CryptoConfigProperties cryptoConfigProperties;
    private final SessionService sessionService;

    @Bean
    public FilterRegistrationBean<SessionFilter> sessionFilter() {
        SessionFilter filter = new SessionFilter(cryptoConfigProperties, sessionService);
        FilterRegistrationBean<SessionFilter> registrationBean = new FilterRegistrationBean<>(filter);

        registrationBean.addUrlPatterns("/*"); // Appliquer à toutes les URLs ou spécifiez un chemin particulier.
        registrationBean.setName("SessionFilter");
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<TokenValidationFilter> tokenValidationFilter() {
        FilterRegistrationBean<TokenValidationFilter> registrationBean = new FilterRegistrationBean<>();

        TokenValidationFilter tvf = new TokenValidationFilter(restTemplate, cryptoConfigProperties);
        registrationBean.setFilter(tvf);

        registrationBean.addUrlPatterns("/*"); // Appliquer à toutes les URLs ou spécifiez un chemin particulier.
        registrationBean.setName("TokenValidationFilter");
        registrationBean.setOrder(2);

        return registrationBean;
    }
}

