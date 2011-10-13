package org.coderthoughts.demo.cdiosgi.web;

import javax.enterprise.context.ApplicationScoped;
import org.coderthoughts.demo.cdiosgi.api.WarBean;
import org.jboss.weld.environment.osgi.api.annotation.Publish;

@Publish
@ApplicationScoped
public class WarBeanImpl implements WarBean {
    public String getValue() {
        return "A value coming from a bean inside a war";
    }
}
