package org.andromeda.torrentsearch;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityUtils {

    private static VelocityEngine ve = null;

    static {
        ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();
    }

    public static final Template getTemplate(String name) {
        Template template = null;

        try {
            template = ve.getTemplate(name + ".vm", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return template;
    }

}
