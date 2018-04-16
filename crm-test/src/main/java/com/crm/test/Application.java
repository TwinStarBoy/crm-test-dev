package com.crm.test;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.alibaba.druid.pool.DruidDataSource;



@EnableAutoConfiguration
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan
@EnableTransactionManagement 
@MapperScan("com.crm.test.mapper")  //后期加上
@ImportResource("/spring/applicationContext.xml")
public class Application {
	private static Logger logger = Logger.getLogger(Application.class);

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {


		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*.xml"));

		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
		// return new
		// DataSourceTransactionManager(dataSourceConfig.dataSource());
	}

	/**
	 * Start
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		logger.info("SpringBoot Start Success");
	}

	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String className;

	/**
	 * druid初始化
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Primary // 默认数据源
	@Bean(name = "dataSource", destroyMethod = "close")
	public DruidDataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(className);
		try {
			dataSource.init();
		} catch (SQLException e) {
			throw new RuntimeException("druid datasource init fail");
		}
		return dataSource;
	}
}

@Configuration
@ImportResource("/spring/applicationContext.xml")
class XmlImportingConfiguration {
}