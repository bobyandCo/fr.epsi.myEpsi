package fr.epsi.myEpsi.listeners;


import java.lang.management.ManagementFactory;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epsi.myEpsi.beans.Utilisateur;
import fr.epsi.myEpsi.mbeans.Premier;

/**
 * Application Lifecycle Listener implementation class StartupListener
 *
 */
@WebListener
public class StartupListener implements ServletContextListener {

	private static final Logger logger = LogManager.getLogger(StartupListener.class);
	
    /**
     * Default constructor. 
     */
    public StartupListener() {
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent event)  { 
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event)  { 
    	logger.error("Test de l'application");
    	try {
    		logger.error( "Chargement du driver..." );
        	Class.forName("org.hsqldb.jdbcDriver");
        	logger.error( "Driver charg� !" );
    	} catch ( ClassNotFoundException e) {
    	   	logger.error( "Erreur lors du chargement : le driver n'a pas �t� trouv� dans le classpath ! <br/>"+ e.getMessage() );
    	}
    	/* Connexion � la base de donn�es */
    	String url = "jdbc:hsqldb:hsql://localhost:9003";
    	String userbsd = "SA";
    	String passwordbsd = "";
    		
    	/*D�claration objets*/
    	Connection connexion = null;
    	Statement statement = null;
    	ResultSet resultat = null;
    	
    	/*D�claration variables*/
    	String idUtilisateur = "";
    	String mdpUtilisateur = "";
    	String isAdministrateur = "";
    	String nomUtilisateur = "";
    	
    	Utilisateur users1 = new Utilisateur();
    	//Array<Utilisateurs> users = new Array();
    	    
    	try {
    	  	logger.error( "Connexion � la base de donn�es..." );
    	   	connexion = DriverManager.getConnection( url, userbsd, passwordbsd );
    	    logger.error("Connexion OK");

    	    /* Cr�ation des objets g�rants les requ�tes */
    	    statement = connexion.createStatement();
    	    logger.error( "Objet requ�te cr�� !" );
                
    	    /* Ex�cution d'une requ�te de lecture */
    	    resultat = statement.executeQuery( "SELECT ID, PASSWORD, ISADMINISTRATOR, NAME FROM UTILISATEURS;" );
    	    logger.error( "Requ�te \"SELECT ID, PASSWORD, ISADMINISTRATOR, NAME FROM UTILISATEURS;\" effectu�e !" );
    	 
    	    logger.error(resultat.toString());
            
            
    	    /* R�cup�ration des donn�es du r�sultat de la requ�te de lecture */
    	    while ( resultat.next() ) {
    	    	idUtilisateur = resultat.getString( "ID" );            
    	    	mdpUtilisateur = resultat.getString( "PASSWORD" );
    	    	isAdministrateur = resultat.getString( "ISADMINISTRATOR" );
    	       	nomUtilisateur = resultat.getString( "NAME" );
    	       	
    	       	users1.setAdministrateur(resultat.getBoolean("ISADMINISTRATOR"));
    	       	users1.setNom(resultat.getString("NAME"));
    	       	users1.setId(idUtilisateur);
    	       	users1.setPassword(mdpUtilisateur);
                
    	       	/* Formatage des donn�es pour affichage dans la JSP finale. */
    	        logger.error( "Donn�es retourn�es par la requ�te : ID = " + idUtilisateur + ", PASSWORD = " + mdpUtilisateur + ", ISADMINISTRATOR = " + isAdministrateur + ", NAME = " + nomUtilisateur + "." );
    	        System.out.println(users1.toString());    	        
    	    }
    	    
    	    logger.error(nomUtilisateur);   
    	    
    	} catch ( SQLException e ) {
    	   	logger.error( "Erreur lors de la connexion : <br/>"+ e.getMessage() );
    	} finally {
    	   	logger.error( "Fermeture de l'objet ResultSet." );
    	    if ( resultat != null ) {
    	    	try {
    	    		resultat.close();
    	        } catch ( SQLException ignore ) {}
    	    }
    	    logger.error( "Fermeture de l'objet Statement." );
    	    if ( statement != null ) {
    	    	try {
    	    		statement.close();
    	        } catch ( SQLException ignore ) {}
    	    }
    	    logger.error( "Fermeture de l'objet Connection." );
    	    if ( connexion != null ) {
    	    	try {
    	    		connexion.close();
    	        } catch ( SQLException ignore ) {}
    	    }
    	}
    	
    	MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    	ObjectName name = null;
        
    	try {
    	    name = new ObjectName("fr.epsi.jmx:type=PremierMBean");
    	    Premier mbean = new Premier();

    	    mbs.registerMBean(mbean, name);

    	}
    	catch (MalformedObjectNameException e) {
    	    e.printStackTrace();
    	}
    	catch (NullPointerException e) {
    	    e.printStackTrace();
    	}
    	catch (InstanceAlreadyExistsException e) {
    	    e.printStackTrace();
    	}
    	catch (MBeanRegistrationException e) {
    	    e.printStackTrace();
    	}
    	catch (NotCompliantMBeanException e) {
    	    e.printStackTrace();
    	}
    	
    }
	
}
