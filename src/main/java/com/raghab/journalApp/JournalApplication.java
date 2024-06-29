package com.raghab.journalApp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(JournalApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager jvayeni(MongoDatabaseFactory mongoDatabaseFactory){
		 return  new MongoTransactionManager(mongoDatabaseFactory);
    }

}
