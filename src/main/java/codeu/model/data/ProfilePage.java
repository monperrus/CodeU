package codeu.model.data;

import java.util.ArrayList;
import java.util.UUID;

public class ProfilePage {
	
	String aboutMe;
	String name;
	User user;
	UUID userID;
	ArrayList<Conversation> recentConvos; 

	/**
	 * Constructs a new Profile Page for a specific user
	 * @param user The user this Page is being made for
	 */
	public ProfilePage(User user){
		this.user = user;
		name = user.getName();
		recentConvos = new ArrayList<Conversation>();
		//return user;
	}
	
	/**Returns the name of the user whose ProfilePage this is*/
	public String getName() {
		return name;
	}

	/**Sets the name of the user whose ProfilePage this is*/
	public void setName(String name) {
		this.name = name;
	}

	/**Returns the "about me" of the user whose ProfilePage this is*/
	public String getAboutMe() {
		return aboutMe;
	}
	
	/**Sets the "about me" of the user whose ProfilePage this is*/
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	
	/**Returns the recent conversations of the user whose ProfilePage this is*/
	public ArrayList<Conversation> getRecentConvos() {
		return recentConvos;
	}

	/**Sets the recent conversations of the user whose ProfilePage this is*/
	public void setRecentConvos(ArrayList<Conversation> recentConvos) {
		this.recentConvos = recentConvos;
	}

}
