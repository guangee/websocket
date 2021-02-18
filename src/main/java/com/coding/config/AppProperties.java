package com.coding.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guanweiming
 */
@Data
@ConfigurationProperties("ws")
public class AppProperties {
    /**
     * title
     */
    private String title;

    /**
     * host
     */
    private String host;

    /**
     * port
     */
    private Integer port;

    /**
     * bossCount
     */
    private int bossCount;

    /**
     * workCount
     */
    private int workCount;

    /**
     * allowCustomRequests
     */
    private boolean allowCustomRequests;

    /**
     * upgradeTimeout
     */
    private int upgradeTimeout;

    /**
     * pingTimeout
     */
    private int pingTimeout;

    /**
     * pingInterval
     */
    private int pingInterval;
}
