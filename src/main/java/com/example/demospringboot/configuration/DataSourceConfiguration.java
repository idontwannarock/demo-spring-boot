package com.example.demospringboot.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.example.demospringboot.persistence"})
@MapperScan(basePackages = {"com.example.demospringboot.persistence.mapper"}, sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfiguration {

    @Autowired
    private Environment environment;

    @Primary
    @Bean(name = "jpaDataSourceProperties")
    @ConfigurationProperties("spring.datasource.jpa")
    public DataSourceProperties jpaDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public HikariDataSource jpaDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setPersistenceUnitName("jpaEntityManager");
        factoryBean.setDataSource(jpaDataSource(jpaDataSourceProperties()));
        factoryBean.setPackagesToScan("com.example.demospringboot.persistence");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", environment.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.format_sql", environment.getProperty("spring.jpa.properties.hibernate.format_sql"));
        factoryBean.setJpaPropertyMap(properties);
        return factoryBean;
    }

    @Primary
    @Bean(name = "jpaTransactionManager")
    public DataSourceTransactionManager jpaTransactionManager() {
        return new DataSourceTransactionManager(jpaDataSource(jpaDataSourceProperties()));
    }

    @Bean(name = "mybatisDataSourceProperties")
    @ConfigurationProperties("spring.datasource.mybatis")
    public DataSourceProperties mybatisDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public HikariDataSource mybatisDataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(mybatisDataSource(mybatisDataSourceProperties()));
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "mybatisTransactionManager")
    public DataSourceTransactionManager mybatisTransactionManager() {
        return new DataSourceTransactionManager(mybatisDataSource(mybatisDataSourceProperties()));
    }
}
