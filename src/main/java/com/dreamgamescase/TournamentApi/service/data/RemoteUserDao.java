package com.dreamgamescase.TournamentApi.service.data;

import com.dreamgamescase.TournamentApi.model.UserModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class RemoteUserDao implements UserDao{

    private static final String BASE_URL = "http://localhost:8080";
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserModel saveClaimRewardRequest(int userId, int rewardCoin) {

        String uri = BASE_URL + "/user/claim-reward/" + userId;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Integer> map= new LinkedMultiValueMap<>();
        map.add("coin", rewardCoin);
        HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<>(map, headers);
        return restTemplate.postForObject(uri,request, UserModel.class);
    }
}
