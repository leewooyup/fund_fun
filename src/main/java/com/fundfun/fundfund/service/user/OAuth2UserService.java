package com.fundfun.fundfund.service.user;

import com.fundfun.fundfund.domain.user.Role;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.user.UserContext;
import com.fundfun.fundfund.exception.OAuthTypeMatchNotFoundException;
import com.fundfun.fundfund.exception.UserNotFoundException;
import com.fundfun.fundfund.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String oauthId = oAuth2User.getName();

        Users user = null;
        String oauthType = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        if (!"KAKAO".equals(oauthType)) {
            throw new OAuthTypeMatchNotFoundException();
        }

        if (isNew(oauthType, oauthId)) { // 새로운 회원이면
            switch (oauthType) {
                case "KAKAO":
                    Map attributesProperties = (Map) attributes.get("properties");
                    Map attributesKakaoAcount = (Map) attributes.get("kakao_account");
                    String nickname = (String) attributesProperties.get("nickname");
                    String email = String.format("%s@kakao.com", oauthId);
                    String username = String.format("KAOKAO_%s", oauthId);

                    if ((boolean) attributesKakaoAcount.get("has_email")) {
                        email = (String) attributesKakaoAcount.get("email");
                    }

                    user = Users.builder()  // 새로운 User를 생성 및 저장
                            .email(email)
                            .name(username)
                            .password("")
                            .build();

                    userRepository.save(user);
//                case "KAKAO" -> {
//                    Map attributesProperties = (Map) attributes.get("properties");
//                    Map attributesKakaoAcount = (Map) attributes.get("kakao_account");
//                    String nickname = (String) attributesProperties.get("nickname");
//                    String email = String.format("%s@kakao.com", oauthId);
//                    String username = String.format("KAOKAO_%s", oauthId);
////                    String email = "%s@kakao.com".formatted(oauthId);   // 해당 형식의 이메일과
////                    String username = "KAKAO_%s".formatted(oauthId);    // 해당 형식의 username을 만들어서
//
//                    if ((boolean) attributesKakaoAcount.get("has_email")) {
//                        email = (String) attributesKakaoAcount.get("email");
//                    }
//
//                    user = Users.builder()  // 새로운 User를 생성 및 저장
//                            .email(email)
//                            .name(username)
////                            .username(username)
//                            .password("")
//                            .build();
//
//                    userRepository.save(user);
//                }
            }
        } else {  // 기존 사용자면
            user = userRepository.findByEmail(String.format("%s_%s",oauthType, oauthId))
//            user = userRepository.findByEmail("%s_%s".formatted(oauthType, oauthId)) //username으로 찾기
                    .orElseThrow(UserNotFoundException::new);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Integer.valueOf(Role.COMMON.getValue()).toString()));
        return new UserContext(user, authorities, attributes, userNameAttributeName);
    }

    private boolean isNew(String oAuthType, String oAuthId) {
        return userRepository.findByEmail(String.format("%s_%s",oAuthType, oAuthId)).isEmpty();
    }
}
