package es.libreria.gaia.user;


import java.util.List;
import org.keycloak.representations.idm.UserRepresentation;

/**
 * Esta clase es una implementacion de {@link gaiaUser} para obtener un {@link UserRepresentation}
 * como representacion de un usuario del producto Gaia.
 * 
 * @author ACING DIM XLIV
 * @version v1.0.0
 * @see GaiaUser
 * @see UserRepresentation
 *
 */



public class GaiaUserRepresentation implements GaiaUser {


  UserRepresentation userRepresentation;

  public GaiaUserRepresentation(UserRepresentation userRepresentation) {
    this.userRepresentation = userRepresentation;
  }

  private UserRepresentation getUserRepresentation() {
    return userRepresentation;
  }

  @Override
  public String getUserId() {

    return getUserRepresentation().getId();
  }

  @Override
  public String getFirstname() {

    return getUserRepresentation().getFirstName();
  }

  @Override
  public String getLastname() {

    return getUserRepresentation().getLastName();
  }

  @Override
  public List<String> getRoles() {

    return getUserRepresentation().getRealmRoles();
  }

  @Override
  public String getUsername() {
    return getUserRepresentation().getUsername();
  }

  @Override
  public String getEmail() {
    return getUserRepresentation().getEmail();
  }

}
