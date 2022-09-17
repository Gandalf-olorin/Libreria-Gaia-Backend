package es.lanyu.sauron.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import es.lanyu.sauron.user.SauronUser;
import es.lanyu.sauron.user.SauronUserImpl;
import es.lanyu.sauron.user.SauronUserRepresentation;

/**
 * Esta clase es un servicio @Service, especialización de @Component, que
 * proporciona un usuario {@link SauronUser} autenticado y sus roles, tambien proporciona un {@link SauronUserRepresentation} mediante
 * el metodo {@link getUserProfil()}, así como todos los roles de la aplicacion donde se encuentra securizado el cliente mediante
 * el metodo {@link getRolesRealm()}.
 * @author ACING DIM XLII
 * @version v2.1.1
 * @see es.lanyu.sauron.SauronUser
 * @see es.lanyu.sauron.SauronUserRepresentation
 * 
 */
@Service
public class SauronServiceImpl implements SauronService {

	@Value("${sauron.admin-cli}")
	private String adminClient;
	
	@Value("${sauron.username-admin}")
	private String admin;
	
	@Value("${sauron.admin-pass}")
	private String adminPass;
	
	@Value("${keycloak.auth-server-url}")
	private String urlServer;
	
	@Value("${keycloak.realm}")
	private String realm;
		
	@SuppressWarnings("unchecked")
	@Override
	public SauronUser getCurrentUser() {
		SauronUserImpl sauronUserImpl = new SauronUserImpl();

		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();

		sauronUserImpl.setUserId(principal.getKeycloakSecurityContext().getToken().getSubject());
		sauronUserImpl.setUsername(principal.getKeycloakSecurityContext().getToken().getPreferredUsername());
		sauronUserImpl.setEmail(principal.getKeycloakSecurityContext().getToken().getEmail());
		sauronUserImpl.setFirstname(principal.getKeycloakSecurityContext().getToken().getFamilyName());
		sauronUserImpl.setLastname(principal.getKeycloakSecurityContext().getToken().getGivenName());
		sauronUserImpl.setRoles(authorities.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList()));
		
		Collection<String> rolesRenombrados = new ArrayList<>();
		sauronUserImpl.getRoles().forEach(r -> {
			String rolRenombrado = r.replace("ROLE_", "");
			rolesRenombrados.add(rolRenombrado);
		});
		sauronUserImpl.getRoles().clear();
		sauronUserImpl.getRoles().addAll(rolesRenombrados);

		
		return sauronUserImpl;
	}
	
	@Override
	public List<String> getCurrentUserRoles() {
		return getCurrentUser().getRoles();
	}

	@Override
	public Keycloak getKeycloak() {
		// Accedo al servidor construyendo una instancia al cliente keycloak-admin que es una cuenta
		// cuya única misión es proporcionar datos del servidor. Realiza la verificación del backend
		// mediante credentials secret, no es necesario ningun usuario autenticado.
		Keycloak kc = KeycloakBuilder.builder().serverUrl(urlServer).realm("master").username(admin)
                .password(adminPass).clientId(adminClient).resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
		
		return kc;
	}
	
	@Override
    public RealmResource getRealmResource(){
        return getKeycloak().realm(realm);
    }

    @Override
	public UsersResource getUsersResource(){
        return getRealmResource().users();
    }
	
	@Override
	public List<String> getRolesRealm() {
		
		RolesResource rolesResource = getRealmResource().roles();

		List<RoleRepresentation> roleRepresentationList = rolesResource.list();

		List<String> roles = roleRepresentationList.stream().map(r -> r.getName().toUpperCase()).collect(Collectors.toList());

		return roles;
		
	}
	@Override
	public List<SauronUser> getUsersRealm() {
		List<SauronUser> sauronUsers = new ArrayList<SauronUser>();
		List<UserRepresentation> representationUsers = getUsersResource().list();
		representationUsers.stream().forEach(u -> {
			SauronUserRepresentation usuario = new SauronUserRepresentation(u);
			sauronUsers.add(usuario);
		});
				
		return sauronUsers;
		
	}
	
	//TODO
	/* Obtiene los usuarios con un rol dado.
	 * @return Set<UserRepresentation>
	 */
	@Override
	public List<SauronUserRepresentation> getUsersWithRol (String rol) {
		
		Set<UserRepresentation> usuariosConRol = new HashSet<UserRepresentation>();
		usuariosConRol = getRealmResource().roles().get(rol).getRoleUserMembers();
		
		List<SauronUserRepresentation> sauronUsersConRol = new ArrayList<SauronUserRepresentation>();
		usuariosConRol.stream().forEach(u -> {
			SauronUserRepresentation usuario = new SauronUserRepresentation(u);
			sauronUsersConRol.add(usuario);
		});
		
		
		return sauronUsersConRol;
		
	}
	
	

	
	/* Obtiene el perfil del usuario autenticado.
	 * @return SauronUserRepresentation
	 * @see es.lanyu.sauron.service.SauronService#getUserProfile()
	 */
	@Override
	public SauronUserRepresentation getUserProfile() {
		
		UserResource userResource = getUsersResource().get(getCurrentUser().getUserId());
		
		UserRepresentation userRepresentation = userResource.toRepresentation();
		userRepresentation.setRealmRoles(getCurrentUser().getRoles());

		return new SauronUserRepresentation(userRepresentation);
	}
	
}