package nl.joost.xmluploader.config;

import javax.sql.DataSource;
import nl.joost.xmluploader.validator.XmlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "nl.joost.xmluploader")
@EnableTransactionManagement
public class ApplicationConfig {

  @Bean
  public XmlValidator xmlValidator() {
    return new XmlValidator();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
      JpaVendorAdapter jpaVendorAdapter) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan("nl.joost.xmluploader.model");
    em.setJpaVendorAdapter(jpaVendorAdapter);
    return em;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    return new HibernateJpaVendorAdapter();
  }
}


