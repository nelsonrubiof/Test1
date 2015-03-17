package com.scopix.periscope.periscopefoundation.exception.notification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;

import com.mindscapehq.raygun4java.core.RaygunClient;

public class RaygunNotifier {

	private String key;
	private String version;
	private Boolean isActive;
	private RaygunClient raygunClient;
	private ArrayList<String> systemTags;
	private static volatile RaygunNotifier INSTANCE = null;
	private static final Logger log = Logger.getLogger(RaygunNotifier.class);

	private RaygunNotifier() {
		log.info("start");
		try {
			PropertiesConfiguration configuration = new PropertiesConfiguration("system.properties");
			configuration.setReloadingStrategy(new FileChangedReloadingStrategy());

			this.key = configuration.getString("RAYGUN_KEY");
			this.version = configuration.getString("RAYGUN__VERSION");
			this.isActive = configuration.getBoolean("RAYGUN_IS_ACTIVE");
			this.systemTags = new ArrayList<String>(Arrays.asList(configuration.getString("RAYGUN_TAGS").split(" , ")));

			raygunClient = new RaygunClient(key);
			raygunClient.SetVersion(version);

		} catch (ConfigurationException e) {
			log.warn("Unable to start Raygun, disabling", e);
			isActive = false;
		} catch (NoSuchElementException e){
			log.warn("Unable to start Raygun, disabling", e);
			isActive = false;
		}
		log.info("end");
	}

	public static RaygunNotifier getInstance() {
		if (INSTANCE == null) {
			synchronized (RaygunNotifier.class) {
				if (INSTANCE == null)
					INSTANCE = new RaygunNotifier();
			}
		}
		return INSTANCE;
	}
	
	public void notifyServer(Throwable throwable, List<String> tagsFromException) {
		log.info("start, isActive: [" + isActive + "]");
		if (isActive) {			
			List<String> allTags = new ArrayList<String>();
			if(systemTags != null && !systemTags.isEmpty()){
				allTags.addAll(systemTags);
			}
			if(tagsFromException !=null && !tagsFromException.isEmpty()){
				allTags.addAll(tagsFromException);
			}
			raygunClient.Send(throwable, allTags);
		}
		log.info("end");
	}
}