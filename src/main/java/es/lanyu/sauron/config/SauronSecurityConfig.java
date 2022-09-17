package es.lanyu.sauron.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * Esta clase de configuración proporciona una adaptador JAVA Spring-Security
 *  para poder hacer uso de un servidor IAM Sauron.
 * @author ACING DIM XLII
 * @version v2.1.0
 * @see KeycloakWebSecurityConfigurerAdapter
 */
@KeycloakConfiguration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, 		//permite utilizar la anotación pre/post de Spring Security.
							securedEnabled = true,		//permite utilizar la anotación @Secured de Spring Security.
							jsr250Enabled = true)		//permite utilizar la anotación @RolesAllowed, nativa de Java.
@PropertySource("classpath:sauronBasic.properties")
public class SauronSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
	
	/**
	 * Proporciona como proveedor de autenticacion el producto Sauron al entorno de seguridad.
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider provider = new KeycloakAuthenticationProvider();
        provider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(provider);
    }

//	Comprueba que exista una sesion valida en las peticiones Http
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    //Permite configurar la seguridad basada en web para http requests específicos. 
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        super.configure(http);
        http.authorizeRequests()
            .anyRequest().permitAll();
        http.csrf().disable();
    }

    /**
     * Proporciona un KeycloakDeployment para autenticar y autorizar las peticiones en base a las propiedades
     * configuradas en el archivo .properties.
     * @return KeycloakConfigResolver
     */
    @Bean
    public KeycloakConfigResolver keycloakConfigResolver(){
        return new KeycloakSpringBootConfigResolver();
    }
    
}