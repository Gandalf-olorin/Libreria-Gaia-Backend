package es.lanyu.sauron.service;

import java.util.Collection;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import es.lanyu.sauron.user.SauronUser;
import es.lanyu.sauron.user.SauronUserRepresentation;

/**
 * Esta interfaz proporciona un usuario autenticado en el servidor Sauron con su perfil
 * y sus roles, asi como la lista de todos los roles de la aplicación donde se encuentra el cliente securizado. 
 * @author ACING DIM XLII
 * @version v2.1.1 
 * @see SauronUser
 */
public interface SauronService {

	Keycloak getKeycloak();
	
	SauronUser getCurrentUser();
	
	Collection<String> getCurrentUserRoles();
	
	SauronUserRepresentation getUserProfile();
	
	RealmResource getRealmResource();
	
	UsersResource getUsersResource();
	
	Collection<String> getRolesRealm();
	
	Collection<SauronUserRepresentation> getUsersWithRol (String rol);
	
	Collection<SauronUser> getUsersRealm();

}