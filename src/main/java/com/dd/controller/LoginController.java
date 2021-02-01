package com.dd.controller;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.dd.data.ApiAccessVO;
import com.dd.data.AppStatusInfo;
import com.dd.data.Login;
import com.dd.service.AuthService;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {
	private static int hit=0;
	private static int random=(int)(Math.random()*100);
	String newline="\\n";
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Value("${spring.application.name}")
	private String applicationName;
	
	@Value("${myapp.msg}")
	private String msg;
	
	@Autowired
	AuthService authService;
	
	/*
	@PostMapping(path="/logout")
	public ResponseEntity<?> login(@RequestBody Login login) {
		if(null==login || StringUtils.isEmpty(login.getUsername())
				|| StringUtils.isEmpty(login.getPassword())) {
			return new ResponseEntity(null,HttpStatus.UNAUTHORIZED);
		}
		logger.info("** LoginController.login.username:" + login.getUsername()+ ",password" + login.getPassword());
		String tokenDetails=null;
		try {
			//byte[] plainCredsBytes = srvProducerScret.getBytes();
			//Encoder encoder = Base64.getEncoder();
			//byte[] base64CredsBytes = encoder.encode(plainCredsBytes);
			//String base64Creds = new String(base64CredsBytes);
			ApiAccessVO apiAccessVO = authService.authenticate_user(login.getUsername(), login.getPassword());
			logger.info("** LoginController.login.apiAccessVO:" + apiAccessVO);
			System.out.println("** LoginController.login.apiAccessVO:" + apiAccessVO);
			return new ResponseEntity<ApiAccessVO>(apiAccessVO,HttpStatus.OK);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
    	return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	*/
	
	//{"username":"ddas","refresh_token":"xxxxx","session_state":"xxxxx"}
	@PostMapping(path="/logout")
	public ResponseEntity<?> logout(@RequestBody ApiAccessVO vo) {
		if(null==vo || StringUtils.isEmpty(vo.getSession_state())) {
			return new ResponseEntity(null,HttpStatus.UNAUTHORIZED);
		}
		logger.info("** LoginController.logout.session:" + vo.getSession_state());
		try {
			ApiAccessVO apiAccessVO = authService.logout_user_session(vo);
			logger.info("** LoginController.login.apiAccessVO:" + apiAccessVO);
			return new ResponseEntity<ApiAccessVO>(apiAccessVO,HttpStatus.OK);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
    	return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(path="/access-token-from-refresh")
	public ResponseEntity<?> accessTokenFromRefresh(@RequestBody ApiAccessVO apiAccessVO) {
		if(null==apiAccessVO || StringUtils.isEmpty(apiAccessVO.getRefresh_token())) {
			return new ResponseEntity(null,HttpStatus.UNAUTHORIZED);
		}
		logger.info("** LoginController.accessTokenFromRefresh.refresh_token:" + apiAccessVO.getRefresh_token() );
		try {
			ApiAccessVO retVo = authService.get_accessToken_from_refresh(apiAccessVO.getRefresh_token());
			logger.info("** LoginController.accessTokenFromRefresh.apiAccessVO:" + retVo);
			return new ResponseEntity<ApiAccessVO>(retVo,HttpStatus.OK);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
    	return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//{"username":"dibs.das","password":"mypwd"}
	@PostMapping(path="/auth")
	public ResponseEntity<?> login(@RequestBody Login login) {
		if(null==login || StringUtils.isEmpty(login.getUsername())
				|| StringUtils.isEmpty(login.getPassword())) {
			return new ResponseEntity(null,HttpStatus.UNAUTHORIZED);
		}
		logger.info("** LoginController.login.username:" + login.getUsername()+ ",password" + login.getPassword());
		String tokenDetails=null;
		try {
			//byte[] plainCredsBytes = srvProducerScret.getBytes();
			//Encoder encoder = Base64.getEncoder();
			//byte[] base64CredsBytes = encoder.encode(plainCredsBytes);
			//String base64Creds = new String(base64CredsBytes);
			ApiAccessVO apiAccessVO = authService.authenticate_user(login.getUsername(), login.getPassword());
			logger.info("** LoginController.login.apiAccessVO:" + apiAccessVO);
			System.out.println("** LoginController.login.apiAccessVO:" + apiAccessVO);
			return new ResponseEntity<ApiAccessVO>(apiAccessVO,HttpStatus.OK);
		} catch (RestClientException e) {
			e.printStackTrace();
		}
    	return new ResponseEntity(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping("/healthz")
	public String healthz() {
		//logger.info("** healthz Called random:" + random);
	    return String.valueOf(System.currentTimeMillis());
	}
	
	@RequestMapping("/rediness")
	public ResponseEntity<String> rediness() {
		System.out.println("** rediness Called time :" + System.currentTimeMillis());
		//Load large data or configuration files during startup. 
		MultiValueMap<String, String> headers =  new LinkedMultiValueMap<>();
		return new ResponseEntity(System.currentTimeMillis(),HttpStatus.OK);
	}
	
    private AppStatusInfo getAppStatus() {
		hit++;
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    	String tm=ft.format(System.currentTimeMillis());
    	AppStatusInfo appstatus = new AppStatusInfo();
    	appstatus.setHit(String.valueOf(hit));
    	appstatus.setRandom(String.valueOf(random));
    	appstatus.setAppname(applicationName);
    	appstatus.setTime(tm);
    	appstatus.setVersion("Initial");
    	appstatus.setFrompropfile(msg);
    	return appstatus;
    }
	

}
