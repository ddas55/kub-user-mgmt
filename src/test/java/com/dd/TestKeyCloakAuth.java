package com.dd;

import java.util.Date;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.dd.controller.LoginController;
import com.dd.data.ApiAccessVO;
import com.dd.data.AppStatusInfo;

import com.dd.data.Login;
import com.dd.service.AuthService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes=UserMngmtApplication.class)
@SpringBootTest
public class TestKeyCloakAuth {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	Gson gson;
	
	
	@Autowired
	LoginController loginController;
	
	@Autowired
	AuthService authService;
	
	String svcBrandUrl ="http://localhost:8070/ctxbrands/brands/allbrands";
	String token_end_point = "http://192.168.64.4:31516/auth/realms/vehrealm/protocol/openid-connect/token";
	String refresh_token = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlNTQzYWMzMy00ZmJjLTRkZjktOWU3Zi1mYTZkMjI2MTE1NzcifQ.eyJleHAiOjE2MTIwMzkyMjIsImlhdCI6MTYxMjAzNzQyMiwianRpIjoiMDA0OTU1YWItZmMwMy00YTZkLWIxOWUtN2I4MjgxNGQ5OGQwIiwiaXNzIjoiaHR0cDovL2tleWNsb2FrL2F1dGgvcmVhbG1zL3ZlaHJlYWxtIiwiYXVkIjoiaHR0cDovL2tleWNsb2FrL2F1dGgvcmVhbG1zL3ZlaHJlYWxtIiwic3ViIjoiYTAwYzgzYzYtZDQwMC00ZmFhLTgwZDMtOTQzNDA1YjQ5YmE2IiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImNsaWVudC1icmFuZHMtc291cmNlIiwic2Vzc2lvbl9zdGF0ZSI6Ijc3MTEyM2RlLWZkZWYtNDliMC05ZjgxLWE3ODVmYjAzNzYxMyIsInNjb3BlIjoicHJvZmlsZSBlbWFpbCJ9.aEdUjf7nJ16YCO3gE-WjIGCrz2j-xDTGr1TiT9j-DxQ";
	  
	
	/*
	curl -X POST 'http://192.168.64.4:31516/auth/realms/vehrealm/protocol/openid-connect/token' \
	 --header 'Content-Type: application/x-www-form-urlencoded' \
	 --data-urlencode 'grant_type=password' \
	 --data-urlencode 'client_id=client-brands-source' \
	 --data-urlencode 'client_secret=6311dbaf-6fd3-4f55-91de-9ce8c469b985' \
	 --data-urlencode 'username=dibs.das' \
	 --data-urlencode 'password=mypwd'
	 */
	
	@Test
	public void test_logout_user() {
		String session = "2b1b6ee1-eab3-4713-a594-43ba767d01b8";
		String reftoken = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlNTQzYWMzMy00ZmJjLTRkZjktOWU3Zi1mYTZkMjI2MTE1NzcifQ.eyJleHAiOjE2MTIwNjY2ODEsImlhdCI6MTYxMjA2NDg4MSwianRpIjoiMmY1NzhlN2MtOTdiYi00N2Y2LWExNWMtODU4NTc4ZDlkMWZhIiwiaXNzIjoiaHR0cDovL2tleWNsb2FrL2F1dGgvcmVhbG1zL3ZlaHJlYWxtIiwiYXVkIjoiaHR0cDovL2tleWNsb2FrL2F1dGgvcmVhbG1zL3ZlaHJlYWxtIiwic3ViIjoiZWFjNTA4NjgtZWFhZi00MzJiLWFiZDUtNWRjYjBiMDM5YTdiIiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImNsaWVudC1icmFuZHMtc291cmNlIiwic2Vzc2lvbl9zdGF0ZSI6IjJiMWI2ZWUxLWVhYjMtNDcxMy1hNTk0LTQzYmE3NjdkMDFiOCIsInNjb3BlIjoicHJvZmlsZSBlbWFpbCJ9.emmHx5C-f9h9igM59Ub9vVsYzjHOb8LF8dI3Qrm4zoc";		
		//ApiAccessVO apiAccessVO = authService.logout_user_session(reftoken);
		//System.out.println("-- apiAccessVO :" + apiAccessVO);
	}
	@Test
	public void test_authenticate_admin() {
		ApiAccessVO apiAccessVO = authService.authenticate_admin();
		System.out.println("-- apiAccessVO :" + apiAccessVO);
	}
	
	@Test
	public void test_authenticate_user() {
		ApiAccessVO apiAccessVO = authService.authenticate_user("dibs.das","mypwd");
		System.out.println("-- apiAccessVO :" + apiAccessVO);
	}
	
	@Test
	public void test_get_accessToken_from_refresh() {
		ApiAccessVO apiAccessVO = new ApiAccessVO();
		apiAccessVO.setRefresh_token(refresh_token);
		apiAccessVO = authService.get_accessToken_from_refresh(refresh_token);
		System.out.println("-- apiAccessVO :" + apiAccessVO);
	}
	
	@Test
	public void test_login_() {
		Login login = new Login();
		login.setUsername("ddas");//dibs.das
		login.setPassword("mypwd");
		loginController.login(login);
	}
	
	@Test
	public void test_logout() {
		Login login = new Login();
		login.setUsername("ddas");//dibs.das
		login.setPassword("mypwd");
		loginController.login(login);
	}
	
	
	@Test
	public void test_get_brands_with_auth() {
		 String consumerurl="http://localhost:9070/ctxconsumer/consume/brandclient";
	     String access_token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJfaXFjYXg1bTA1UTNiQ19tRnZhdFRTNGhYZloxV0NnN2NTOTU5Y2t0SS00In0.eyJleHAiOjE2MTE2ODIyMzUsImlhdCI6MTYxMTY4MTkzNCwianRpIjoiODg0MTQ4MGQtY2M4YS00NzY4LTgxOTQtMjVlYmIxYTM5ODAyIiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguNjQuNDozMTUxNi9hdXRoL3JlYWxtcy92ZWhyZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJhMDBjODNjNi1kNDAwLTRmYWEtODBkMy05NDM0MDViNDliYTYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjbGllbnQtYnJhbmRzLXNvdXJjZSIsInNlc3Npb25fc3RhdGUiOiJiMzE4M2MzZi03MjBiLTRlNWQtYWNmNi02Njc3NmQzOGJkYjMiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwicm9sZS1yZWFsbS11c2VyIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJjbGllbnQtYnJhbmRzLXNvdXJjZSI6eyJyb2xlcyI6WyJyb2xlLXVzZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoiZGlicy5kYXMifQ.fKnmMfTAXWsipXQhK_UiA0J0sWlIsS0XmAak1ayT8Ufor5dSbKLZjAAjCzvWLmlMKi3fFAIw-8_hLmiaKrHzuSV0ekS_YFR8Zqi7n3V2SxYTUwEp1CSVSiO_gUYEqV4TlStYz5u7nirEMrrrPmQKrMypxwAt8bfu3LrcmecaZVnFA0FELkkCrrS4krBZ6Tai-N-Qp4IEPeP_g85jQxp8ubjuPG5Uwg6xEjqcctXCBwgWohguwQ8xjbFy8kW0rHxk8VHge5JjL3Nj9flKWR_vsutdCTHC4wNTLomPd7Q-RTvMoJFMjE76L2RcSyxEIx_TSwYfbnNRYoRFlcI5Nd0mfw";
	   	 String refresh_token = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlNTQzYWMzMy00ZmJjLTRkZjktOWU3Zi1mYTZkMjI2MTE1NzcifQ.eyJleHAiOjE2MTE2ODM3MzUsImlhdCI6MTYxMTY4MTkzNSwianRpIjoiODhlNDNjM2ItYTI4ZS00OTNhLTk4NTAtZmY4YzlkM2U3NjlhIiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguNjQuNDozMTUxNi9hdXRoL3JlYWxtcy92ZWhyZWFsbSIsImF1ZCI6Imh0dHA6Ly8xOTIuMTY4LjY0LjQ6MzE1MTYvYXV0aC9yZWFsbXMvdmVocmVhbG0iLCJzdWIiOiJhMDBjODNjNi1kNDAwLTRmYWEtODBkMy05NDM0MDViNDliYTYiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoiY2xpZW50LWJyYW5kcy1zb3VyY2UiLCJzZXNzaW9uX3N0YXRlIjoiYjMxODNjM2YtNzIwYi00ZTVkLWFjZjYtNjY3NzZkMzhiZGIzIiwic2NvcGUiOiJwcm9maWxlIGVtYWlsIn0.jQpJC3NT4URDxIwThJPoWiKPlrNtR-mmzuED9uZGoPY";

	     MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
         headers.add("Authorization", "Bearer " + access_token);
         headers.add("refresh_token",  refresh_token);
		 ResponseEntity<AppStatusInfo> responseEntity = 
				 restTemplate.exchange(consumerurl, HttpMethod.GET, new HttpEntity<Object>(headers),AppStatusInfo.class);
		 System.out.println("-- responseEntity :" + responseEntity);
	}
	
	
	@Test
	public void testAuth() {
		ResponseEntity<String> response_token=null;
       	HttpHeaders headers_for_token = new HttpHeaders();
       	headers_for_token.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
       	MultiValueMap<String, String> map_for_token = new LinkedMultiValueMap<>();
       	map_for_token.add("grant_type", "password");
       	map_for_token.add("client_id", "client-brands-source");
      	map_for_token.add("client_secret", "6311dbaf-6fd3-4f55-91de-9ce8c469b985");
       	map_for_token.add("username", "dibs.das");
       	map_for_token.add("password", "mypwd");

       	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map_for_token, headers_for_token);
        String access_token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJfaXFjYXg1bTA1UTNiQ19tRnZhdFRTNGhYZloxV0NnN2NTOTU5Y2t0SS00In0.eyJleHAiOjE2MTE2MTMzNTQsImlhdCI6MTYxMTYxMzA1NCwianRpIjoiODNmODRkNzYtOGMxMC00MDYxLWEwYmMtZjI5MjZkZWJlMDE4IiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguNjQuNDozMTUxNi9hdXRoL3JlYWxtcy92ZWhyZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJhMDBjODNjNi1kNDAwLTRmYWEtODBkMy05NDM0MDViNDliYTYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjbGllbnQtYnJhbmRzLXNvdXJjZSIsInNlc3Npb25fc3RhdGUiOiJiNjIwY2NkNy00NGI3LTRkMGUtODhkNy1hMWI4YTg0NDU3YjEiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwicm9sZS1yZWFsbS11c2VyIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJjbGllbnQtYnJhbmRzLXNvdXJjZSI6eyJyb2xlcyI6WyJyb2xlLXVzZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwicHJlZmVycmVkX3VzZXJuYW1lIjoiZGlicy5kYXMifQ.SSsQsQQpI4pDtwKr3iaESN1xwTEtdmWstAyopCyFgtTRLyAa3Nulx6GfquZveZyLXY4417AbChiVLz4NPmAsd9FNYsc-_SQNAG8VIJOH9M7wQiHT51YzkGBVb5EOhU6anHx18i-D4rONmPGMFYhgl6u0gp8cgdch6ZlfBEXlSC9g8Hb8SQSiqgzsHlKdndq9hxZXZUIGwt3WrM1MD_058dTP3WYAaX0voLdwZnVtqR03_7-vQTi_C6qKNMREC_G2gwfsRNxf1OcOX9uzNSYcX2euo7E9F08_93d_nDu-7-QqOnfRV2T05HZ2LCx6MWoqY3TiH04FVqQNJiM_5w8UZg";
   		String refresh_token = "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlNTQzYWMzMy00ZmJjLTRkZjktOWU3Zi1mYTZkMjI2MTE1NzcifQ.eyJleHAiOjE2MTE2MTI5NTIsImlhdCI6MTYxMTYxMTE1MiwianRpIjoiZDg0M2UyMGEtOTQ1ZC00MzgxLTk2YjAtNTVjNjVhZTEyY2RlIiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguNjQuNDozMTUxNi9hdXRoL3JlYWxtcy92ZWhyZWFsbSIsImF1ZCI6Imh0dHA6Ly8xOTIuMTY4LjY0LjQ6MzE1MTYvYXV0aC9yZWFsbXMvdmVocmVhbG0iLCJzdWIiOiJhMDBjODNjNi1kNDAwLTRmYWEtODBkMy05NDM0MDViNDliYTYiLCJ0eXAiOiJSZWZyZXNoIiwiYXpwIjoiY2xpZW50LWJyYW5kcy1zb3VyY2UiLCJzZXNzaW9uX3N0YXRlIjoiODRjYTI0YjAtNWFlMC00N2NhLWI0M2UtNGJkNWQ4MWRjZGQwIiwic2NvcGUiOiJwcm9maWxlIGVtYWlsIn0.7fKJyKSCsByD65ftxtZuJQoRXefNIKqc-MNjj25UgF4";
       	
	   	try {
	   		response_token = restTemplate.postForEntity(token_end_point , request , String.class);
	   		String resjson = response_token.getBody();
	   		ApiAccessVO tokenvo = gson.fromJson(resjson, ApiAccessVO.class);
	   		System.out.println("-- tokenvo :" + tokenvo);
	   		
	   		
	   		/*
	   		 MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
             headers.add("Authorization", "Bearer " + access_token);
			 HttpStatus statusCode=null;
			 try {
				ResponseEntity<Brand[]> responseEntity = restTemplate.exchange(
						 svcBrandUrl, HttpMethod.GET, new HttpEntity<Object>(headers),Brand[].class);
				brands = responseEntity.getBody();
				statusCode = responseEntity.getStatusCode();
				System.out.println("-- statusCode :" + statusCode);
			  } catch (HttpClientErrorException e) {
				e.printStackTrace();
				statusCode= e.getStatusCode();
			  }
			 
			 //If access_token Token Expired , get the new access_token from refresh token
			 if(statusCode.getReasonPhrase().toLowerCase().equalsIgnoreCase( "Unauthorized".toLowerCase() )) {
			     System.out.println("-- access_token from refresh -- ");
			     HttpHeaders hdrs = new HttpHeaders();
			     hdrs .setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		     	 MultiValueMap<String, String> refresh_token_map = new LinkedMultiValueMap<>();
		     	 refresh_token_map.add("client_id", "client-brands-source");
		     	 refresh_token_map.add("client_secret", "6311dbaf-6fd3-4f55-91de-9ce8c469b985");
		     	 refresh_token_map.add("grant_type", "refresh_token");
				 refresh_token_map.add("refresh_token", refresh_token);
				 HttpEntity<MultiValueMap<String, String>> req = new HttpEntity<>(refresh_token_map, hdrs);
				 response_token = restTemplate.postForEntity(token_end_point , req, String.class);
			   	 System.out.println("-- response_token :" + response_token);
			  }*/
    		
       	} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
