package uol.mini.proxy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uol.mini.proxy.domain.RequestInfo;
import uol.mini.proxy.proxy.HostFilter;

/**
 * Created by cin_dmartins on 10/03/2016.
 */
@Controller
public class ProxyController {

    @Autowired
    private HostFilter hostFilters;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public RequestInfo greeting(RequestInfo message) throws Exception {
        return new RequestInfo("Hello, " + message.getUri() + "!");
    }

    @RequestMapping(value = "/add/host", method = RequestMethod.POST)
    @ResponseBody
    public String addHost(@RequestBody RequestInfo requestInfo) {
        hostFilters.addHost(requestInfo.getUri());
        return requestInfo.getUri();
    }

    @RequestMapping(value = "/add/host", method = RequestMethod.DELETE)
    @ResponseBody
    public String removeHost(@RequestBody RequestInfo requestInfo) {
        hostFilters.remove(requestInfo.getUri());
        return requestInfo.getUri();
    }

}
