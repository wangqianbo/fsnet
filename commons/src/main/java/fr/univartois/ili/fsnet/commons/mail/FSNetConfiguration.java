package fr.univartois.ili.fsnet.commons.mail;

import java.io.File;
import java.util.Properties;

import javax.persistence.EntityManager;

import fr.univartois.ili.fsnet.commons.utils.PersistenceProvider;
import fr.univartois.ili.fsnet.entities.Property;

/**
 * @author FSNet
 * 
 */
public final class FSNetConfiguration {

	public static final String MAIL_FROM_KEY = "fsnet.mail.from";
	public static final String ENABLE_TLS_KEY = "mail.smtp.starttls.enable";
	public static final String SMTP_HOST_KEY = "mail.smtp.host";
	public static final String SMTP_USER_KEY = "mail.smtp.user";
	public static final String SMTP_PASSWORD_KEY = "mail.smtp.password";
	public static final String FSNET_WEB_ADDRESS_KEY = "fsnet.web.address";
	public static final String ENABLE_AUTHENTICATION_KEY = "mail.smtp.auth";
	public static final String SMTP_PORT_KEY = "mail.smtp.port";
	public static final String SSL_KEY = "mail.smtp.socketFactory.class";
	public static final String SSL_ENABLED_VALUE = "javax.net.ssl.SSLSocketFactory";
	public static final String PICTURES_DIRECTORY_KEY = "pictures.dir";
	public static final String KEY_FACEBOOK = "keyfacebook";

	private static final String USER_HOME = "user.home";
	private static final String FSNET_DIRECTORY = ".fsnet";
	private static final String PICTURES_DIRECTORY = "uploaded-images";

	private static final FSNetConfiguration INSTANCE;

	/**
	 * @return a instance of FSNetConfiguration
	 */
	public static final FSNetConfiguration getInstance() {
		return INSTANCE;
	}

	private Properties properties = new Properties();

	static {
		INSTANCE = new FSNetConfiguration();
	}

	/**
	 * Constructor
	 */
	private FSNetConfiguration() {
		refreshConfiguration();
	}

	/**
	 * @return
	 */
	public Properties getFSNetConfiguration() {
		return properties;
	}

	/**
	 * Refresh Configuration
	 */
	public final void refreshConfiguration() {
		properties.clear();
		EntityManager em = PersistenceProvider.createEntityManager();
		Property property;
		property = em.find(Property.class, ENABLE_AUTHENTICATION_KEY);
		if (property != null) {
			properties.put(ENABLE_AUTHENTICATION_KEY, property.getValue());
			if (Boolean.valueOf(property.getValue())) {
				property = em.find(Property.class, SMTP_USER_KEY);
				if (property == null) {
					throw new IllegalStateException();
				}
				properties.put(SMTP_USER_KEY, property.getValue());
				property = em.find(Property.class, SMTP_PASSWORD_KEY);
				if (property == null) {
					throw new IllegalStateException();
				}
				properties.put(SMTP_PASSWORD_KEY, property.getValue());
			}
		}
		property = em.find(Property.class, MAIL_FROM_KEY);
		if (property != null) {
			properties.put(MAIL_FROM_KEY, property.getValue());
		}
		property = em.find(Property.class, SMTP_HOST_KEY);
		if (property != null) {
			properties.put(SMTP_HOST_KEY, property.getValue());
		}
		property = em.find(Property.class, SSL_KEY);
		if (property != null) {
			if (Boolean.valueOf(property.getValue())) {
				properties.put(SSL_KEY, SSL_ENABLED_VALUE);
			}
		}
		property = em.find(Property.class, ENABLE_TLS_KEY);
		if (property != null) {
			properties.put(ENABLE_TLS_KEY, property.getValue());
		}
		property = em.find(Property.class, FSNET_WEB_ADDRESS_KEY);
		if (property != null) {
			properties.put(FSNET_WEB_ADDRESS_KEY, property.getValue());
		}
		property = em.find(Property.class, SMTP_PORT_KEY);
		if (property != null) {
			properties.put(SMTP_PORT_KEY, property.getValue());
		}
		property = em.find(Property.class, PICTURES_DIRECTORY_KEY);
		if (property != null) {
			properties.put(PICTURES_DIRECTORY_KEY, property.getValue());
		} else {
			if (createDefaultImagesDirectory()) {
				properties.put(PICTURES_DIRECTORY_KEY,
						buildDefaultNameOfPicturesDirectory());
			}
		}
		property = em.find(Property.class, KEY_FACEBOOK);
		if (property != null) {
			properties.put(KEY_FACEBOOK, property.getValue());
		}

		em.close();
	}

	/**
	 * @return a boolean
	 */
	public boolean isSSLEnabled() {
		return SSL_ENABLED_VALUE.equals(properties.getProperty(SSL_KEY));
	}

	/**
	 * @return a boolean
	 */
	public boolean isTLSEnabled() {
		return "true".equalsIgnoreCase(properties.getProperty(ENABLE_TLS_KEY));
	}

	/**
	 * @return a boolean
	 */
	public boolean isAuthenticationEnabled() {
		return "true".equalsIgnoreCase(properties
				.getProperty(ENABLE_AUTHENTICATION_KEY));
	}

	/**
	 * Create the default directory for the uploaded images.
	 * 
	 * @return true if the directory is create or it already exist.
	 */
	private boolean createDefaultImagesDirectory() {
		String path = buildDefaultNameOfPicturesDirectory();
		File folder = new File(path);

		return folder.mkdirs() || folder.isDirectory();
	}

	/**
	 * @return a String
	 */
	private String buildDefaultNameOfPicturesDirectory() {
		String path = System.getProperty(USER_HOME) + File.separator
				+ FSNET_DIRECTORY + File.separator + PICTURES_DIRECTORY;
		return path;
	}

}
