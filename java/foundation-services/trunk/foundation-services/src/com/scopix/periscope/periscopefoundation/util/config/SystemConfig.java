package com.scopix.periscope.periscopefoundation.util.config;

import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 * This class administers system configuration, such as deployment parameters.<br> Application configuration is stored on
 * different profile files on classpath.<br> Those property files contains all deployment and configuration parameters.<br> This
 * class knows about finding those parameters and determining which profile file correspond to each machine.
 *
 * @author Nelson Rubio
 * @version 2.0.0
 *
 */
public class SystemConfig {

    private static final String ENV_VAR_PROFILE = "SCOPIX_PROFILE";
    /**
     * Singleton object
     */
    private static SystemConfig instance;
    private static final String PROFILE_PROPERTIES_PATH = "/config/profiles/";

    /**
     *
     * @param key llave de la propiedad solicitada
     * @return Boolena de la llave solicitada
     */
    public static Boolean getBooleanParameter(String key) {
        return Boolean.parseBoolean(getInstance().getProperties().getProperty(key));
    }

    /**
     *
     * @return instancia de SystemConfig
     */
    public static SystemConfig getInstance() {
        if (instance == null) {
            instance = new SystemConfig();
        }
        return instance;
    }

    /**
     * Find {@link Integer} type parameter with specified key
     *
     * @param key llave de la propiedad solicitada
     * @return Integer for requested key
     */
    public static Integer getIntegerParameter(String key) {
        return Integer.parseInt(getInstance().getProperties().getProperty(key));
    }

    /**
     * Find {@link String} type parameter with specified key
     *
     * @param key llave de la propiedad solicitada
     * @return String for requested key
     */
    public static String getStringParameter(String key) {
        return getInstance().getProperties().getProperty(key);
    }

    /**
     *
     * @return Properties in system
     */
    public Properties getProperties() {
        if (this.properties == null) {
            this.properties = this.initProperties();
        }
        return this.properties;
    }

    private String getProfile() {
        if (this.profile == null) {
            this.profile = this.initProfile();
        }
        return this.profile;
    }

    /**
     * This method init specified profile
     */
    private String initProfile() {
        String localProfile;

        String env = System.getenv(ENV_VAR_PROFILE);

        if (env != null) {
            localProfile = env;
            log.info("Usando el profile (variable de entorno): " + localProfile);
        } else {
            localProfile = "server";
            log.info("No se encontro la variable de entorno, usando el profile: " + localProfile);
        }

        return localProfile;
    }

    private Properties initProperties() {
        String localProfile = this.getProfile();
        try {
            Properties localProperties = new Properties();
            localProperties.load(
                    new ClassPathResource(PROFILE_PROPERTIES_PATH + "profile." + localProfile + ".properties").getInputStream());
            return localProperties;
        } catch (IOException e) {
            throw new RuntimeException("Se intento configurar el profile: " + localProfile
                    + ", pero no existe el archivo de properties correcto en la ruta: " + PROFILE_PROPERTIES_PATH
                    + ", con el formato: \"profile.[profile].properties\"");
        }
    }
    private static Logger log = Logger.getLogger(SystemConfig.class);
    private String profile;
    private Properties properties;
}
