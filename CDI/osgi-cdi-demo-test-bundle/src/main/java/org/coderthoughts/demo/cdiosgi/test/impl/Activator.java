package org.coderthoughts.demo.cdiosgi.test.impl;

import org.coderthoughts.demo.cdiosgi.api.OSGiPropertyProvider;
import org.coderthoughts.demo.cdiosgi.api.WarBean;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

public class Activator implements BundleActivator {
    private ServiceRegistration reg;
    private ServiceTracker st;

    public void start(BundleContext context) throws Exception {
        System.out.println("*** osgi-cdi-demo-test-bundle started.");

        // This bundle mocks the JavaEE Service for testing
        WarBean spp = new WarBean() {
            public String getValue(String name) {
                return "Mocked from OSGi Bundle";
            }
        };
        reg = context.registerService(WarBean.class.getName(), spp, null);

        st = new ServiceTracker(context, OSGiPropertyProvider.class.getName(), null) {
            public Object addingService(ServiceReference reference) {
                Object svc = super.addingService(reference);
                if (svc instanceof OSGiPropertyProvider) {
                    System.out.println("*** From OSGi Tester: "
                            + ((OSGiPropertyProvider) svc).getProperty("org.osgi.framework.executionenvironment"));
                }
                return svc;
            }
        };
        st.open();
    }

    public void stop(BundleContext context) throws Exception {
        reg.unregister();
        st.close();
    }
}
