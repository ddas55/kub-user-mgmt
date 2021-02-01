package com.dd.data;

import java.util.List;

public class AppStatusInfo {
	private String hit;
	private String time;
	private String random;
	private String appname;
	private String frompropfile;
	private String version;
	
	public String getHit() {
		return hit;
	}
	public void setHit(String hit) {
		this.hit = hit;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRandom() {
		return random;
	}
	public void setRandom(String random) {
		this.random = random;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getFrompropfile() {
		return frompropfile;
	}
	public void setFrompropfile(String frompropfile) {
		this.frompropfile = frompropfile;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	@Override
	public String toString() {
		return "AppStatusInfo [hit=" + hit + ", time=" + time + ", random=" + random + ", appname=" + appname
				+  ", version=" + version + "]";
	}
	
	
	
}
