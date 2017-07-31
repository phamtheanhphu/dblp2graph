package com.dblp2graph.OGM.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class Neo4jSessionFactory {

	private InputStream inputStream;

	private SessionFactory sessionFactory;

	private static Neo4jSessionFactory factory = new Neo4jSessionFactory();

	public static Neo4jSessionFactory getInstance() {
		return factory;
	}

	// prevent external instantiation
	private Neo4jSessionFactory() {
		try {
			this.sessionFactory = new SessionFactory(this.getConfiguration(), "com.dblp2graph.OGM.entity");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Session getNeo4jSession() {
		return sessionFactory.openSession();
	}

	private Configuration getConfiguration() throws IOException {

		try {

			Properties prop = new Properties();
			String propFileName = "neo4j.ogm.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {

				prop.load(inputStream);

				Configuration configuration = new Configuration.Builder().uri(prop.getProperty("neo4j_url"))
						.credentials(prop.getProperty("neo4j_username"), prop.getProperty("neo4j_password")).build();

				return configuration;
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}

		return null;

	}

}
