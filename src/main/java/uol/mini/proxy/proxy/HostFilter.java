package uol.mini.proxy.proxy;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cin_dmartins on 11/03/2016.
 */
@Component
public class HostFilter {

    private Set<String> hosts;

    public HostFilter() {
        this.hosts = new HashSet<>();
    }

    public void addHost(String host){
        this.hosts.add(host);
    }

    public void remove(String host){
        this.hosts.remove(host);
    }

    public boolean contain(String host){
        return this.hosts.stream().anyMatch(host::contains);
    }
}
