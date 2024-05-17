package aespa.codeeasy.JKglobal.login.service;

import aespa.codeeasy.controller.request.LoginRequest;
import aespa.codeeasy.domain.Member;
import aespa.codeeasy.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));

        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().name())
                .build();
    }

    public void login(LoginRequest request) throws Exception {
        var user = memberRepository.findByEmail(request.getEmail()).orElse(null);
        if(!user.getPassword().equals(new BCryptPasswordEncoder().encode(request.getPassword()))){
            throw new Exception("유저 비밀번호 오류");
        }
    }
}
