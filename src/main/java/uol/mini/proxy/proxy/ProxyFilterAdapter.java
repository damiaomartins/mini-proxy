package uol.mini.proxy.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.littleshoot.proxy.HttpFiltersAdapter;

/**
 * Created by cin_dmartins on 09/03/2016.
 */
public class ProxyFilterAdapter extends HttpFiltersAdapter {

    public ProxyFilterAdapter(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        super(originalRequest, ctx);
    }

    public ProxyFilterAdapter(HttpRequest originalRequest) {
        super(originalRequest);
    }

    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
        return super.clientToProxyRequest(httpObject);
    }


}
