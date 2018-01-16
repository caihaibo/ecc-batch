package com.ecc.batch.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.SQLException;
@Configuration
@MapperScan("com.ecc.batch.dao.*Mapper")
public class DuridConfig {

    private static final Logger logger = LoggerFactory.getLogger(DuridConfig.class);

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.initialSize}")
    private Integer initialSize;
    @Value("${spring.datasource.minIdle}")
    private Integer minIdle;
    @Value("${spring.datasource.maxActive}")
    private Integer macActive;
    @Value("${spring.datasource.maxWait}")
    private Integer maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private Boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private Boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
    private Boolean poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private Integer maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("${druid.allow}")
    private String druidAllow;
    @Value("${druid.deny}")
    private String druidDeny;
    @Value("${druid.login.user.name}")
    private String druidLoginUserName;
    @Value("${druid.login.user.password}")
    private String druidLoginUserPassword;

    @Bean
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        try {
            druidDataSource.setUrl(url);
            druidDataSource.setDriverClassName(driverClassName);
            druidDataSource.setUsername(userName);
            druidDataSource.setPassword(password);
            druidDataSource.setInitialSize(initialSize);
            druidDataSource.setMinIdle(minIdle);
            druidDataSource.setMaxActive(macActive);
            druidDataSource.setMaxWait(maxWait);
            druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            druidDataSource.setValidationQuery(validationQuery);
            druidDataSource.setTestWhileIdle(testWhileIdle);
            druidDataSource.setTestOnBorrow(testOnBorrow);
            druidDataSource.setTestOnReturn(testOnReturn);
            druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
            druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
            druidDataSource.setFilters(filters);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return druidDataSource;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //白名单：
        if(!StringUtils.isEmpty(druidAllow)) {
            servletRegistrationBean.addInitParameter("allow",druidAllow);
        }
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        if(!StringUtils.isEmpty(druidDeny)) {
            servletRegistrationBean.addInitParameter("deny", druidDeny);
        }
        //登录查看信息的账号密码.
        if(!StringUtils.isEmpty(druidLoginUserName)) {
            servletRegistrationBean.addInitParameter("loginUsername", druidLoginUserName);
        }
        if(!StringUtils.isEmpty(druidLoginUserPassword)) {
            servletRegistrationBean.addInitParameter("loginPassword", druidLoginUserPassword);
        }
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }


}
