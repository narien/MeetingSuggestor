package model;

import java.util.HashMap;

import javax.swing.DefaultListModel;

public class UserDatabase extends HashMap<String, User>{

	/**
	 * Honestly no idea what this does, I guess it is hashmap that wants it.
	 */
	private static final long serialVersionUID = 1L;

	public UserDatabase(){
		super();
	}

	public DefaultListModel<User> toListModel() {
		DefaultListModel<User> users = new DefaultListModel<User>();
		for(String s: this.keySet()){
			users.addElement(this.get(s));
		}
		
		return users;
	}
}
