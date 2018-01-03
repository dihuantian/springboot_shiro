package com.example.springboot_shiro.config.http;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.SocketUtils;

/*
 * 同时使用HTTP和HTTPS连接器
 * HTTPS连接器使用properties进行配置
 * HTTP连接器使用编程方式进行配置
 * */
@Configuration
public class HttpConnectionConfig {

    @Bean
    public Integer port(){
        return 8088;
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer(){
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();/*{
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };*/
        factory.addAdditionalTomcatConnectors(createStandardConnector());
        return factory;
    }

    /*获取Tomcat的连接器*/
    private Connector createStandardConnector(){
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        //connector.setScheme("http");
        connector.setPort(port());
        //connector.setSecure(false);
        //设置http转向https
        //connector.setRedirectPort(8080);
        return connector;
    }
}
