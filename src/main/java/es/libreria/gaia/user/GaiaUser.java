package es.libreria.gaia.user;

import java.util.List;

/**
 * Esta interfaz proporciona los datos basicos (perfil) y roles del usuario autenticado en el servidor gaia. 
 * @author ACING DIM XLII
 * @version v1.0.0 
 */
public interface GaiaUser {

	String getUserId();

	String getUsername();
	
	String getFirstname();
	
	String getLastname();

	String getEmail();

	List<String> getRoles();

}