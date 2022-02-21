package com.aem.jam.core.servlets;

import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Iterator;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Simple Servlet",
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/isAdminServlet"
        })
public class AdminUserServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserServlet.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req,
                         final SlingHttpServletResponse resp) throws IOException {
        boolean isAdminUser = false;
        ResourceResolver resourceResolver = req.getResourceResolver();
        Session session = resourceResolver.adaptTo(Session.class);
        try {
            User currentUser = resourceResolver.adaptTo(User.class);
            Iterator<Group> currentUserGroups = currentUser.memberOf();
            while (currentUserGroups.hasNext()) {
                Group grp = currentUserGroups.next();
                if(grp.getID().equalsIgnoreCase("administrators")){
                    isAdminUser = true;
                    break;
                }
            }
            if(session != null) {
                session.logout();
            }
        }catch (RepositoryException e) {
            LOGGER.error("AccessDeniedException", e);
        }
        resp.setContentType("text/plain");
        resp.getWriter().write(String.valueOf(isAdminUser));
    }

}
