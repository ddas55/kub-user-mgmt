package com.dd.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ApiAccessVO {
	
	
	private String client_id;
	private String client_secret;
	private String grant_type;
	private String access_token;
	private String refresh_token;
	private String expires_in;
	private String refresh_expires_in;
	private String token_type;
	private String session_state;
	private String currenttime;
	private String username;
	private boolean session_logout;
	private String error;
	private String error_description;
	private int httpstatus;
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_secret() {
		return client_secret;
	}
	public void setClient_secret(String client_secret) {
		this.client_secret = client_secret;
	}
	public String getGrant_type() {
		return grant_type;
	}
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getRefresh_expires_in() {
		return refresh_expires_in;
	}
	public void setRefresh_expires_in(String refresh_expires_in) {
		this.refresh_expires_in = refresh_expires_in;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public String getSession_state() {
		return session_state;
	}
	public void setSession_state(String session_state) {
		this.session_state = session_state;
	}
	public String getCurrenttime() {
		return this.currenttime;
	}
	public void setCurrenttime(String currenttime) {
		this.currenttime = currenttime;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isSession_logout() {
		return session_logout;
	}
	public void setSession_logout(boolean session_logout) {
		this.session_logout = session_logout;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getError_description() {
		return error_description;
	}
	public void setError_description(String error_description) {
		this.error_description = error_description;
	}
	public int getHttpstatus() {
		return httpstatus;
	}
	public void setHttpstatus(int httpstatus) {
		this.httpstatus = httpstatus;
	}
	@Override
	public String toString() {
		return "ApiAccessVO [client_id=" + client_id + ", client_secret=" + client_secret + ", grant_type=" + grant_type
				+ ", access_token=" + access_token + ", refresh_token=" + refresh_token + ", expires_in=" + expires_in
				+ ", refresh_expires_in=" + refresh_expires_in + ", token_type=" + token_type + ", session_state="
				+ session_state + ", currenttime=" + currenttime + ", username=" + username + ", session_logout="
				+ session_logout + ", error=" + error + ", error_description=" + error_description + ", httpstatus="
				+ httpstatus + "]";
	}
	
	

}
