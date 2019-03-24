package com.andband.auth.security.oauth2;

import com.andband.auth.config.SecurityProperties;
import com.andband.auth.persistence.user.Role;
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
    private SecurityProperties securityProperties;

    public AuthorizationServerConfig(AuthenticationManager authenticationManager,
                                     TokenStore tokenStore,
                                     TokenEnhancer tokenEnhancer,
                                     PasswordEncoder passwordEncoder,
                                     SecurityProperties securityProperties) {
        this.authenticationManager = authenticationManager;
        this.tokenStore = tokenStore;
        this.tokenEnhancer = tokenEnhancer;
        this.passwordEncoder = passwordEncoder;
        this.securityProperties = securityProperties;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("web")
                .secret(passwordEncoder.encode(securityProperties.getClientWebPassword()))
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .autoApprove(true)
                .accessTokenValiditySeconds(30000)
                .refreshTokenValiditySeconds(-1)
                .and()
                .withClient("internal-api")
                .secret(passwordEncoder.encode(securityProperties.getClientInternalApiPassword()))
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
