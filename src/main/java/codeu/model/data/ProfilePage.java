package codeu.model.data;

import java.util.ArrayList;
import java.util.UUID;

public class ProfilePage {
	
	String aboutMe;
	String name;
	User user;
	UUID userID;
	ArrayList<Conversation> recentConvos; 
	
	public ArrayList<Conversation> getRecentConvos() {
		return recentConvos;
	}


	public void setRecentConvos(ArrayList<Conversation> recentConvos) {
		this.recentConvos = recentConvos;
	}


	public ProfilePage(User user){
		this.user = user;
		name = user.getName();
		recentConvos = new ArrayList<Conversation>();
		//return user;
	}
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAboutMe() {
		return aboutMe;
	}
	
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
}
