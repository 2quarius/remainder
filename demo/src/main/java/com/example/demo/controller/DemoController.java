package com.example.demo.controller;

import com.sun.deploy.net.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@RestController
public class DemoController {
    @GetMapping(value = "/demo")
    public int demo(){
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setClientSecret("3708E0E6D34C0BE0ECFB547C2C89CB1926EA1AE937BA204F");
        resourceDetails.setClientId("3q6TNuBfQXWJ8XypOTNx");
        resourceDetails.setAccessTokenUri("https://jaccount.sjtu.edu.cn/oauth2/token");
        List<String> scope = new ArrayList<>();
        scope.add("basic");
        scope.add("essential");
        scope.add("classes");
        resourceDetails.setScope(scope);

        OAuth2RestTemplate oAuthRestTemplate = new OAuth2RestTemplate(resourceDetails);

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );

        OAuth2AccessToken token = oAuthRestTemplate.getAccessToken();
        System.out.println(oAuthRestTemplate.getResource());
        System.out.println(oAuthRestTemplate.getOAuth2ClientContext());
        System.out.println(token);
        return 0;
    }
}
