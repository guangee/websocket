package com.coding.config;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guanweiming
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({
        AppProperties.class,
})
public class AppConfiguration {

    @Bean
    public SocketIOServer socketIoServer(AppProperties appProperties) {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname(appProperties.getHost());
        config.setPort(appProperties.getPort());
        config.setBossThreads(appProperties.getBossCount());
        config.setWorkerThreads(appProperties.getWorkCount());
        config.setAllowCustomRequests(appProperties.isAllowCustomRequests());
        config.setUpgradeTimeout(appProperties.getUpgradeTimeout());
        config.setPingTimeout(appProperties.getPingTimeout());
        config.setPingInterval(appProperties.getPingInterval());
        return new SocketIOServer(config);
    }
}
