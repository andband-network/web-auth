package com.andband.auth.security.oauth2;

import com.andband.auth.persistence.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private TokenStore tokenStore;
    private TokenEnhancer tokenEnhancer;
    private PasswordEncoder passwordEncoder;

    @Value("${andband.auth.client.web.client-id}")
    private String clientWebId;

    @Value("${andband.auth.client.web.client-secret}")
    private String clientWebSecret;

    @Value("${andband.auth.client.internal-api.client-id}")
    private String clientInternalApiId;

    @Value("${andband.auth.client.internal-api.client-secret}")
    private String clientInternalApiSecret;

    public AuthorizationServerConfig(AuthenticationManager authenticationManager,
                                     TokenStore tokenStore,
                                     TokenEnhancer tokenEnhancer,
                                     PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenStore = tokenStore;
        this.tokenEnhancer = tokenEnhancer;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientWebId)
                .secret(passwordEncoder.encode(clientWebSecret))
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .autoApprove(true)
                .accessTokenValiditySeconds(30000)
                .refreshTokenValiditySeconds(-1)
                .and()
                .withClient(clientInternalApiId)
                .secret(passwordEncoder.encode(clientInternalApiSecret))
                .authorizedGrantTypes("client_credentials")
                .authorities(Role.INTERNAL_API.getName())
                .scopes("read", "write", "trust")
                .autoApprove(true)
                .accessTokenValiditySeconds(30000)
                .refreshTokenValiditySeconds(-1);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancer);
    }

}
