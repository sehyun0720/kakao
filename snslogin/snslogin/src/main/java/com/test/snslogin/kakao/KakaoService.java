package com.test.snslogin.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Mult;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
@Slf4j
public class KakaoService{
    public User getKakaoToken(String code) throws IOException{
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "eb62d23b6cfb5bf0525736bdcdd0639f");
        body.add("redirect_uri", "http://localhost:8080/token");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        ResponseEntity<JSONObject> apiResponse = rt.postForEntity("https://kauth.kakao.com/oauth/token", kakaoTokenRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        String access_token =(String) responseBody.get("access_token");
        return getKakaoInfo(access_token);
    }

    public User getKakaoInfo(String access_token) throws IOException{
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + access_token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");

        HttpEntity<MultiValueMap<String, String>> kakaoInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return User.builder()
                .id(jsonNode.get("id").toString())
                .nickname(jsonNode.get("properties").get("nickname").toString())
                .email(jsonNode.get("kakao_account").get("email").toString())
                .build();
    }
}
