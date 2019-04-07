package com.chyl.mytest.redis;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author chyl
 * @create 2018-04-27 下午5:33
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfig {

    private String host;

    private Integer port;

    private String password;

//    @Value("${spring.redis.jedis.pool.active}")
//    private String active;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer idle;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = getRedisConfig();
        JedisPool jedisPool;
        if (StringUtils.isNotBlank(password)) {
             jedisPool = new JedisPool(config, host, port,2000,password);
        }else {
             jedisPool = new JedisPool(config, host);
        }
        return jedisPool;
    }

    @Bean
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(idle);
        return config;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
