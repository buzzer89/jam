package com.aem.jam.core.servlets;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.servlet.MockSlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class PageTitleServletTest {

    @Mock
    SlingHttpServletRequest request;

    @Mock
    ResourceResolver resourceResolver;

    @Mock
    Page page;

    @InjectMocks
    private PageTitleServlet pageTitleServlet = new PageTitleServlet();

    @BeforeEach
    void setUp() {
    }

    @Test
    void doGetTest(AemContext context) throws IOException {
        MockSlingHttpServletResponse response = context.response();

        when(request.getResourceResolver()).thenReturn(resourceResolver);
        when(resourceResolver.adaptTo(Page.class)).thenReturn(page);
        when(page.getPageTitle()).thenReturn("New Project");

        pageTitleServlet.doGet(request, response);
        assertEquals("New Project", response.getOutputAsString());
    }

    /*@Test
    void testPageisNull(AemContext context) throws IOException {

        MockSlingHttpServletResponse response = context.response();

//        when(request.getResourceResolver()).thenReturn(resourceResolver);
//        when(resourceResolver.adaptTo(Page.class)).thenReturn(null);

        pageTitleServlet.doGet(request, response);
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {

            }
        });
    }*/
}