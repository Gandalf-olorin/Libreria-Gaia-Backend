package es.libreria.gaia.service;

import java.util.Collection;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import es.libreria.gaia.user.GaiaUser;
import es.libreria.gaia.user.GaiaUserRepresentation;

/**
 * Esta interfaz proporciona un usuario autenticado en el servidor Gaia con su perfil
 * y sus roles, asi como la lista de todos los roles de la aplicación donde se encuentra el cliente securizado. 
 * @author ACING DIM XLII
 * @version v2.1.1 
 * @see GaiaUser
 */
public interface GaiaService {

	Keycloak getKeycloak();
	
	GaiaUser getCurrentUser();
	
	Collection<String> getCurrentUserRoles();
	
	GaiaUserRepresentation getUserProfile();
	
	RealmResource getRealmResource();
	
	UsersResource getUsersResource();
	
	Collection<String> getRolesRealm();
	
	Collection<GaiaUserRepresentation> getUsersWithRol (String rol);
	
	Collection<GaiaUser> getUsersRealm();

}