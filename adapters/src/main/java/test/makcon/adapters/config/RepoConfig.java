package test.makcon.adapters.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "test.makcon.adapters.repository")
public class RepoConfig {
}
