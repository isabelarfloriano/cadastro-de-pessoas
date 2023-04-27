package com.java.backend.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataBaseConfig {

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/db?useSSL=false&createDatabaseIfNotExist=true");
    dataSource.setUsername("root");
    dataSource.setPassword("password");
    return dataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
}

  @Bean
  public String criaTabelaSql() {
      return "CREATE TABLE IF NOT EXISTS pessoas_cadastradas (\n" +
              "  id INT PRIMARY KEY AUTO_INCREMENT,\n" +
              "  nome VARCHAR(50) NOT NULL,\n" +
              "  email VARCHAR(50) NOT NULL,\n" +
              "  telefone VARCHAR(50) NOT NULL,\n" +
              "  profissao VARCHAR(50) NOT NULL\n" +
              ")";
  }
}

