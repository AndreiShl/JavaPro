package ru.inno.javapro.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class AppConfig {
    @Bean
    HikariDataSource HikariDataSource() {
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", "postgres");
        props.setProperty("dataSource.password", "postgres");
        props.setProperty("dataSource.databaseName", "postgres");
        props.setProperty("dataSource.url", "jdbc:postgresql://localhost/postgres");
        HikariConfig config = new HikariConfig(props);
        config.setSchema("hw4");
        return new HikariDataSource(config);
    }
}
