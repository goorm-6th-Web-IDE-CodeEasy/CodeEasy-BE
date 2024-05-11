package aespa.codeeasy.config.ouath;

import aespa.codeeasy.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class PrincipalDetails implements OAuth2User {
    private Member member;
    private Map<String, Object> attributes;

    // OAuth 로그인

    public PrincipalDetails(Member member, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        authorityList.add(authority);

        return authorityList;
    }

    @Override
    public String getName() {
        return member.getId() + "";
    }
}
