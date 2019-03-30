package com.andband.auth.security.oauth2;

import com.andband.auth.security.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.HashMap;
import java.util.Map;

public class AccessTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Object principal = authentication.getPrincipal();

        Map<String, Object> additionalInfo = new HashMap<>();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            additionalInfo.put("clientId", "web");
            additionalInfo.put("accountId", userDetails.getAccountId());
        } else {
            additionalInfo.put("clientId", principal);
        }

        DefaultOAuth2AccessToken auth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
        auth2AccessToken.setAdditionalInformation(additionalInfo);
        String encodedToken = encode(auth2AccessToken, authentication);
        auth2AccessToken.setValue(encodedToken);

        return accessToken;
    }

}
