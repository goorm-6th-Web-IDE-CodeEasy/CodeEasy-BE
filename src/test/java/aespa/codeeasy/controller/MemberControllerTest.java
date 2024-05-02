package aespa.codeeasy.controller;

import aespa.codeeasy.dto.MemberDto;
import aespa.codeeasy.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MemberControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    void registerUser_success() throws Exception {
        MemberDto memberDto = new MemberDto("1", "nickname", "email@example.com", "password");
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"memberId\":1,\"nickname\":\"nickname\",\"email\":\"email@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void registerUser_failure_badRequest() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nickname\":\"\", \"email\":\"not-an-email\", \"password\":\"\"}")) // 유효하지 않은 이메일과 빈 필드
                .andExpect(status().isBadRequest());
    }

}