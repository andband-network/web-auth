package com.andband.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:com/andband/auth/config/security.properties")
public class SecurityProperties {

    @Value("${andband.auth.access-token.signing-key}")
    private String accessTokenSigningKey;

    @Value("${andband.auth.client.internal-api.password}")
    private String clientInternalApiPassword;

    @Value("${andband.auth.client.web.password}")
    private String clientWebPassword;

    public String getAccessTokenSigningKey() {
        return accessTokenSigningKey;
    }

    public String getClientInternalApiPassword() {
        return clientInternalApiPassword;
    }

    public String getClientWebPassword() {
        return clientWebPassword;
    }

}
