package com.dd.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.dd.data.ApiAccessVO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

@Service
public class AuthService {
	
	//Keycloak
	@Value("${keycloak.resource}")
	private String client_id;
	
	@Value("${keycloak.credentials.secret}")
	private String client_secret;
	
	@Value("${myapp.keycloak.token-endpoint}")
	private String token_end_point;
	
	//admin
	@Value("${myapp.keycloak.admin-userid}")
	private String admin_userid;
	
	@Value("${myapp.keycloak.admin-password}")
	private String admin_password;
	
	
	@Value("${myapp.keycloak.admin-token-endpoint}")
	private String admin_token;
	
	@Value("${myapp.keycloak.logout-user-session}")
	private String logout_user_session;
	
	@Value("${myapp.keycloak.logout-user-all-session}")
	private String logout_user_all_session;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	Gson gson;
	
	String pattern = "yyyy-MMM-dd HH:mm:ss.SSS";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
	
	
	/*
	public ApiAccessVO logout_user_all_sessions(String username,String userid) {
    	ApiAccessVO tokenvo=null;
   	 	HttpHeaders hdrs = new HttpHeaders();
   	 	hdrs.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      	MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
      	 try {
   			HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map,hdrs);
   			restTemplate.delete((logout_user_all_session+session_state));
   			tokenvo = new ApiAccessVO();
   			tokenvo.setUsername(username);
   			tokenvo.setSession_logout(true);
   			String currenttime = simpleDateFormat.format(new Date());
   			tokenvo.setCurrenttime(currenttime);
   		} catch (RestClientException e) {
   			e.printStackTrace();
   		} catch (JsonSyntaxException e) {
   			e.printStackTrace();
   		}
   	    return tokenvo;
     }
     */
	
    public ApiAccessVO logout_user_session(ApiAccessVO vo) {
    	ApiAccessVO tokenvo=null;
   	 	HttpHeaders hdrs = new HttpHeaders();
   	 	hdrs.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      	MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
      	ResponseEntity<String> response_token=null;
      	 try {
			map.add("grant_type", "refresh_token");
			map.add("refresh_token", vo.getRefresh_token());  		
			map.add("client_id", client_id);
      		map.add("client_secret", client_secret);
      		map.add("session_state", vo.getSession_state());
   			HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map,hdrs);
   			response_token = restTemplate.postForEntity(logout_user_session , req, String.class);
   			int status = 0;
   			if(null!=response_token && null!=response_token.getStatusCode() ) {
   				status =  response_token.getStatusCode().value();
   			}
   			if(status==204) {
   	   			tokenvo = new ApiAccessVO();
   	   			tokenvo.setUsername(vo.getUsername());
   	   			tokenvo.setSession_logout(true);
   	   			String currenttime = simpleDateFormat.format(new Date());
   	   			tokenvo.setCurrenttime(currenttime);
   			}
      	} catch (RestClientException e) {
   			e.printStackTrace();
   		} catch (JsonSyntaxException e) {
   			e.printStackTrace();
   		}
   	    return tokenvo;
     }
    
	
    public ApiAccessVO authenticate_user(String userid,String pwd) {
    	ApiAccessVO tokenvo=null;
   	 	HttpHeaders hdrs = new HttpHeaders();
   	 	hdrs.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      	MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
      	 try {
      		map.add("grant_type", "password");
      		map.add("client_id", client_id);
      		map.add("client_secret", client_secret);
   			map.add("username", userid);//dibs.das
   			map.add("password", pwd);//mypwd
   			HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map,hdrs);
   			ResponseEntity<String> response_token = restTemplate.postForEntity(token_end_point , req, String.class);
   			String resjson = response_token.getBody();
   			tokenvo = gson.fromJson(resjson, ApiAccessVO.class);
   			tokenvo.setClient_id(null);
   			tokenvo.setClient_secret(null);
   			tokenvo.setGrant_type(null);
   			String currenttime = simpleDateFormat.format(new Date());
   			tokenvo.setCurrenttime(currenttime);
   		} catch (RestClientException e) {
   			e.printStackTrace();
   		} catch (JsonSyntaxException e) {
   			e.printStackTrace();
   		}
   	    return tokenvo;
     }
	
    public ApiAccessVO get_accessToken_from_refresh(String ref_token) {
     ApiAccessVO tokenvo=null;
	 HttpHeaders hdrs = new HttpHeaders();
	 hdrs.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
   	 MultiValueMap<String, String> refresh_token_map = new LinkedMultiValueMap<>();
   	 try {
			 refresh_token_map.add("client_id", client_id);
			 refresh_token_map.add("client_secret", client_secret);
			 refresh_token_map.add("grant_type", "refresh_token");
			 refresh_token_map.add("refresh_token", ref_token);
			 HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(refresh_token_map, hdrs);
			 ResponseEntity<String> response_token = restTemplate.postForEntity(token_end_point , req, String.class);
			 tokenvo = gson.fromJson(response_token.getBody(), ApiAccessVO.class);
			 String currenttime = simpleDateFormat.format(new Date());
			 tokenvo.setCurrenttime(currenttime);
   	 	}catch (HttpClientErrorException e) {
			 tokenvo = new ApiAccessVO();
			 tokenvo.setError(e.getMessage());
			 String currenttime = simpleDateFormat.format(new Date());
			 tokenvo.setCurrenttime(currenttime);
			 tokenvo.setHttpstatus(e.getRawStatusCode());
			
		} catch (RestClientException e) {
			 tokenvo = new ApiAccessVO();
			 tokenvo.setError(e.getMessage());
			 String currenttime = simpleDateFormat.format(new Date());
			 tokenvo.setCurrenttime(currenttime);
		} catch (JsonSyntaxException e) {
			 tokenvo = new ApiAccessVO();
			 tokenvo.setError(e.getMessage());
			 String currenttime = simpleDateFormat.format(new Date());
			 tokenvo.setCurrenttime(currenttime);
		}
	    return tokenvo;
   }
    
    
   public ApiAccessVO authenticate_admin() {
    	ApiAccessVO tokenvo=null;
   	 	HttpHeaders hdrs = new HttpHeaders();
   	 	hdrs.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      	MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
      	 try {
      		map.add("grant_type", "password");
      		map.add("client_id", "admin-cli");
   			map.add("username", admin_userid);//admin
   			map.add("password", admin_password);//admin
   			HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(map,hdrs);
   			ResponseEntity<String> response_token = restTemplate.postForEntity(admin_token , req, String.class);
   			String resjson = response_token.getBody();
   			tokenvo = gson.fromJson(resjson, ApiAccessVO.class);
   			String currenttime = simpleDateFormat.format(new Date());
   			tokenvo.setCurrenttime(currenttime);
   		} catch (RestClientException e) {
   			e.printStackTrace();
   		} catch (JsonSyntaxException e) {
   			e.printStackTrace();
   		}
   	    return tokenvo;
     }

}
