package de.onvif;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.xml.soap.SOAPException;

import org.onvif.ver10.schema.PTZPreset;

import de.onvif.soap.OnvifDevice;

public class OnvifPTZ {
	
	String username;
	String password;
	String ip;
	
	OnvifDevice nvt;
	String ptzAddress;
	String profileToken;
	
	
	public OnvifPTZ(String ip,String username,String password){
		this.username=username;
		this.password=password;
		this.ip=ip;
	}
	
	public boolean initPTZ(){
		try {
			nvt = new OnvifDevice(ip, username,password);
			ptzAddress = nvt.getCapabilities().getPTZ().getXAddr();
			profileToken = nvt.getDevices().getProfiles().get(0).getToken();
		} catch (ConnectException | SOAPException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void moveLeft(float x){
		nvt.getPtz().continuousMove(profileToken, -x, 0, 0);
	}
	
	public void moveRight(float x){
		nvt.getPtz().continuousMove(profileToken, x, 0, 0);
	}
	
	public void moveUp(float y){
		nvt.getPtz().continuousMove(profileToken, 0, y, 0);
	}
	
	public void moveDown(float y){
		nvt.getPtz().continuousMove(profileToken, 0, -y, 0);
	}
	
	public void moveUpLeft(float x,float y){
		nvt.getPtz().continuousMove(profileToken, -x,y, 0);
	}
	
	public void moveUpRight(float x,float y){
		nvt.getPtz().continuousMove(profileToken, x,y, 0);
	}
	
	public void moveDownLeft(float x,float y){
		nvt.getPtz().continuousMove(profileToken, -x,-y, 0);
	}
	
	public void moveDownRight(float x,float y){
		nvt.getPtz().continuousMove(profileToken, x,-y, 0);
	}
	
	public void zoomIn(float z){
		nvt.getPtz().continuousMove(profileToken, 0,0,z);
	}
	
	public void zoomOut(float z){
		nvt.getPtz().continuousMove(profileToken, 0,0,-z);
	}
	
	public void stop(){
		nvt.getPtz().stopMove(profileToken);
	}
	
	public void goToPreset(int i){
		List<PTZPreset> presetList = nvt.getPtz().getPresets(profileToken);
		nvt.getPtz().gotoPreset(presetList.get(i-1).getToken(), profileToken);
	}
	
	public void setPreset(int i){
		nvt.getPtz().setPreset(String.valueOf(i), String.valueOf(i), profileToken);
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		OnvifPTZ onvif = new OnvifPTZ("10.6.1.34", "admin", "admin");
		System.out.println(new Date().toString());
		onvif.initPTZ();
		System.out.println(new Date().toString());
//		onvif.setPreset(1);
		onvif.goToPreset(1);
//		onvif.moveDown(0.5f);
//		Thread.sleep(5000);
//		onvif.stop();
		
	}
}
