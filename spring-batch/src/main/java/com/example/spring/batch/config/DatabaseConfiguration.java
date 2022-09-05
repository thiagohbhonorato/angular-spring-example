package com.example.spring.batch.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.example.spring.batch.constant.Constants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(value = Constants.PACKAGES_TO_SCAN, entityManagerFactoryRef = "batchEntityManagerFactory")
@EnableTransactionManagement
public class DatabaseConfiguration {

  private final Environment env;

  public DatabaseConfiguration(Environment env) {
    this.env = env;
  }

  @Bean(Constants.BATCH_DATABASE)
  public DataSource batchDataSource() {
    HikariConfig config = new HikariConfig();
    config.setPoolName(Constants.BATCH_DATABASE);
    config.setJdbcUrl(env.getRequiredProperty("spring.datasource.url"));
    config.setUsername(env.getProperty("spring.datasource.username"));
    config.setPassword(env.getProperty("spring.datasource.password"));
    config.setMinimumIdle(env.getProperty("spring.datasource.min-idle", Integer.class, 2));
    config.setMaximumPoolSize(env.getProperty("spring.datasource.max-active", Integer.class, 100));
    config.setTransactionIsolation("TRANSACTION_READ_COMMITTED");
    config.setRegisterMbeans(true);
    return new HikariDataSource(config);
  }

  @Bean(Constants.BATCH_JPA_VENDOR_ADAPTER)
  public JpaVendorAdapter batchJpaVendorAdapter() {
    return new HibernateJpaVendorAdapter();
  }

  @Bean(Constants.BATCH_ENTITY_MANAGER_FACTORY)
  public LocalContainerEntityManagerFactoryBean batchEntityManagerFactory() {
    LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
    emfBean.setDataSource(batchDataSource());
    emfBean.setPackagesToScan(Constants.PACKAGES_TO_SCAN);
    emfBean.setBeanName(Constants.BATCH_ENTITY_MANAGER_FACTORY);
    emfBean.setJpaVendorAdapter(batchJpaVendorAdapter());

    Properties jpaProps = new Properties();
    jpaProps.put("hibernate.physical_naming_strategy",
        env.getProperty("spring.jpa.hibernate.naming.physical-strategy"));
    jpaProps.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto", "none"));
    jpaProps.put("hibernate.jdbc.fetch_size",
        env.getProperty("spring.jpa.properties.hibernate.jdbc.fetch_size", "200"));

    Integer batchSize = env.getProperty("spring.jpa.properties.hibernate.jdbc.batch_size", Integer.class, 100);
    if (batchSize > 0) {
      jpaProps.put("hibernate.jdbc.batch_size", batchSize);
      jpaProps.put("hibernate.order_inserts", "true");
      jpaProps.put("hibernate.order_updates", "true");
    }

    jpaProps.put("hibernate.show_sql", env.getProperty("spring.jpa.properties.hibernate.show_sql", "false"));
    jpaProps.put("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql", "false"));

    emfBean.setJpaProperties(jpaProps);
    return emfBean;
  }

  @Bean(Constants.BATCH_TRANSACTION_MANAGER)
  public PlatformTransactionManager transactionManager() {
    EntityManagerFactory object = batchEntityManagerFactory().getObject();
    if (object != null) {
      return new JpaTransactionManager(object);
    } else {
      return null;
    }
  }

  @Bean
  public MBeanExporter exporter() {
    final MBeanExporter exporter = new MBeanExporter();
    exporter.setExcludedBeans(Constants.BATCH_DATABASE);
    return exporter;
  }
}
