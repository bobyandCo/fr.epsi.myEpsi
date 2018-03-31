package fr.epsi.myEpsi.dao;
import java.util.ArrayList;

import fr.epsi.myEpsi.beans.Utilisateur;;

public class UserDAO {
	private ArrayList<Utilisateur> u1 = new ArrayList<Utilisateur>();
	
	public void SetUsers (ArrayList<Utilisateur> users) {
		u1 = users;
	}

}
