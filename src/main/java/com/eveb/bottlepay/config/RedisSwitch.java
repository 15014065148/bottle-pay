package com.eveb.bottlepay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisSwitch {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public StringRedisTemplate stringRedisTemplate_0()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(0);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_1()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(1);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_2()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(2);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_3()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(3);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_4()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(4);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_5()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(5);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_6()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(6);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_7()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(7);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_8()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(8);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_9()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(9);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_10()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(10);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_11()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(11);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_12()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(12);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_13()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(13);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_14()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(14);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate_15()
    {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(jedisConnectionFactory.getHostName());
        jedis.setPort(jedisConnectionFactory.getPort());
        jedis.setDatabase(15);
        jedis.setPoolConfig(jedisConnectionFactory.getPoolConfig());
        jedis.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(jedis);
        return template;
    }

}
