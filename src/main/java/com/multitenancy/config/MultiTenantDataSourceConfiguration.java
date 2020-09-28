package com.multitenancy.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.multitenancy.MultitenancyAbstractdsApplication;

@Configuration
@EnableTransactionManagement
public class MultiTenantDataSourceConfiguration {

	@Autowired
	private MultiTenantProperties multiTenantProperties;

	@Bean
	public Map<String, DataSource> repositoryDataSources() {
		Map<String, DataSource> datasources = new HashMap<>();
		multiTenantProperties.getDatasources().forEach((key, value) -> datasources.put(key, createDataSource(value)));
		return datasources;
	}

	@Bean
	@Primary
	public DataSource dataSource(Map<String, DataSource> repositoryDataSources) {
		Map<Object, Object> dataSourceMap = new HashMap<>();
		MultiTenantDataSourceRouting routing = new MultiTenantDataSourceRouting();
		repositoryDataSources.forEach((key, value) -> dataSourceMap.put(key, value));
		routing.setDefaultTargetDataSource(repositoryDataSources.get("tenant1"));
		routing.setTargetDataSources(dataSourceMap);
		return routing;
	}

	private DataSource createDataSource(Map<String, String> source) {
		return DataSourceBuilder.create().url(source.get("url")).driverClassName(source.get("driverClassName"))
				.username(source.get("username")).password(source.get("password")).build();
	}

	@Bean
	public JpaTransactionManager multiTenantTxManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
		return transactionManager;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
			DataSource dataSource) {
		return builder.dataSource(dataSource).packages(MultitenancyAbstractdsApplication.class.getPackageName())
				.build();
	}

}
