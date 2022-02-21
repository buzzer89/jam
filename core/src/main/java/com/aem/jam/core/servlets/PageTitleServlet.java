package com.aem.jam.core.servlets;

import com.day.cq.wcm.api.Page;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Page Servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.resourceTypes="+ "jam/components/structure/page",
                "sling.servlet.extensions=" + "txt"
        })
public class PageTitleServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageTitleServlet.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws IOException {
        ResourceResolver resourceResolver = req.getResourceResolver();
        try {
            Page page = resourceResolver.adaptTo(Page.class);
            resp.setContentType("text/plain");
            resp.getWriter().write(String.valueOf(page.getPageTitle()));
        }catch(Exception e) {
            LOGGER.error("Error getting Page Title", e);
        }
    }
}
