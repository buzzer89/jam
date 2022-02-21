package com.aem.jam.core.servlets;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class AdminUserServletTest {

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    SlingHttpServletRequest request;

    @Mock
    Session session;

    @Mock
    User user;

    @Mock
    Group group;

    @InjectMocks
    private AdminUserServlet adminUserServlet = new AdminUserServlet();

    @BeforeEach
    void setUp() {
    }

    @Test
    void doGetTest(AemContext context) throws IOException, RepositoryException {
        Iterator currentUserGroups = mock(Iterator.class);
        MockSlingHttpServletResponse response = context.response();
        when(request.getResourceResolver()).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
        when(resourceResolver.adaptTo(User.class)).thenReturn(user);
        when(user.memberOf()).thenReturn(currentUserGroups);
        when(currentUserGroups.hasNext()).thenReturn(true,false);
        when(currentUserGroups.next()).thenReturn(group);
        when(group.getID()).thenReturn("administrators");
        adminUserServlet.doGet(request, response);
        assertEquals("true",response.getOutputAsString());
    }
}