package com.cuongpn.security.oauth2;

import com.cuongpn.entity.Role;
import com.cuongpn.entity.User;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.repository.RoleRepository;
import com.cuongpn.repository.UserRepository;
import com.cuongpn.security.Jwt.JwtProvider;
import com.cuongpn.security.services.UserPrincipal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${client.domain.url}")
    private String clientUrl;
    @Value("${app.default.avatar.url}")
    private String defaultAvatar;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String,Object> attributes =  defaultOAuth2User.getAttributes();
        String provider = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        OAuth2AuthorizedClient authorizedClient = oAuth2AuthorizedClient.loadAuthorizedClient(
                oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(),
                oAuth2AuthenticationToken.getName()
        );
        String authorize_token = authorizedClient.getAccessToken().getTokenValue();
        System.out.println(authorize_token);
        UserAttributes userAttributes = OAuth2AttributeMapper.mapAttributes(provider,attributes,authorize_token);
        for(Map.Entry<String,Object> entry : attributes.entrySet()){
            System.out.println(entry.getKey() +  "=> " + entry.getValue());
        }
        User user = userRepository.findByEmail(userAttributes.email()).orElseGet(()->{
                    Role role = roleRepository.findByName("ROLE_USER")
                            .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED));
                    User newUser = User.builder()
                            .email(userAttributes.email())
                            .name(userAttributes.name())
                            .avatarUrl(userAttributes.avatar())
                            .roles(Set.of(role))
                            .isVerification(true)
                            .provider(provider)
                            .providerId(userAttributes.providerId())
                            .build();
                    return userRepository.save(newUser);
                }
        );

        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken",accessToken)
                .path("/")
                .httpOnly(true)
                .maxAge(60*60)
                .sameSite("Strict")
                .secure(true)
                .build();
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken",refreshToken)
                .path("/")
                .httpOnly(true)
                .maxAge(60*60*24)
                .sameSite("Strict")
                .secure(true)
                .build();
        response.addHeader("Set-Cookie",accessTokenCookie.toString());
        response.addHeader("Set-Cookie",refreshTokenCookie.toString());
        response.setStatus(HttpServletResponse.SC_FOUND);
        this.setAlwaysUseDefaultTargetUrl(true);
        this.setDefaultTargetUrl(clientUrl);
        super.onAuthenticationSuccess(request,response,authentication);
        
    }
}
