package uol.mini.proxy.proxy;

import com.google.common.base.Charsets;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import uol.mini.proxy.domain.RequestInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by cin_dmartins on 09/03/2016.
 */
@Component
public class ProxyFilterSource extends HttpFiltersSourceAdapter {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private HostFilter hostFilters;

    @Override
    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        String strURI = originalRequest.getUri();
        if (StringUtils.isNotBlank(strURI) && hostFilters.contain(strURI)) {
            RequestInfo payload = new RequestInfo();
            try {
                URI uri = new URI(strURI);
                payload.setUri(uri.getHost());
                payload.setMethod(originalRequest.getMethod().name());
                payload.setTimestamp(LocalDateTime.now());

                List<NameValuePair> params = URLEncodedUtils.parse(uri, Charsets.UTF_8.displayName());
                params.forEach(param -> payload.addParam(param.getName(), param.getValue()));

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            messagingTemplate.convertAndSend("/topic/greetings", payload);
        }
        return new ProxyFilterAdapter(originalRequest, ctx);
    }

    @Override
    public int getMaximumResponseBufferSizeInBytes() {
        return 10 * 1024 * 1024;
    }
}
