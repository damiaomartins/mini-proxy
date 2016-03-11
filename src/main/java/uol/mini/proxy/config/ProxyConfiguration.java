package uol.mini.proxy.config;

import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uol.mini.proxy.proxy.ProxyFilterSource;

/**
 * Created by cin_dmartins on 09/03/2016.
 */
@Configuration
public class ProxyConfiguration {

    @Bean
    public HttpProxyServer proxyServer(ProxyFilterSource proxyFilterSource) {
        return DefaultHttpProxyServer.bootstrap()
                .withPort(8080)
                .withFiltersSource(proxyFilterSource)
                .withAllowLocalOnly(false)
                .withName("UOLProxy")
                .start();
    }

}
