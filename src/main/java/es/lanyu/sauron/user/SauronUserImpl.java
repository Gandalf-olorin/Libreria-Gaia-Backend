package es.lanyu.sauron.user;

import java.util.List;

/**
 * Esta clase es la implementacion de {@link SauronUser} con los datos basicos y sus roles.
 * @author ACING DIM XLII
 * @version v1.0.0
 * @see SauronUser
 * 
 */
public class SauronUserImpl implements SauronUser {

    private String userId;
    private String username;
    private String email;
    private String firstname;
    private String lastname;    
    private List<String> roles;
	
    @Override
	public String getUserId() {
		return userId;
	}
	
    public void setUserId(String userId) {
		this.userId = userId;
	}
	
    @Override
	public String getUsername() {
		return username;
	}
	
    public void setUsername(String username) {
		this.username = username;
	}
    
    
	
    public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String getEmail() {
		return email;
	}
	
    public void setEmail(String email) {
		this.email = email;
	}
	
    @Override
	public List<String> getRoles() {
		return roles;
	}
	
    public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Usuario autenticado: [UserId()=" + getUserId() + ", Username()=" + getUsername() + ", Firstname()="
				+ getFirstname() + ", Lastname()=" + getLastname() + ", Email()=" + getEmail() + ", Roles()="
				+ getRoles() + "]";
	}

	

	
    
    
}
