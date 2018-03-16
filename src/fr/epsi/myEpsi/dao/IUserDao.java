package fr.epsi.myEpsi.dao;

import fr.epsi.myEpsi.beans.Utilisateur;

public interface IUserDao {

	boolean create(Utilisateur utilisateur);
	boolean delete(Utilisateur utilisateur);
	Utilisateur get(String id);
	boolean check(Utilisateur utilisateur);
	
}
