package io.hdavid.blogservice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class BlogServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        BlogPage blogPage = null;
        String uri = req.getRequestURI();
        if (uri.equals("/")) {
            blogPage = new WelcomePage();

        }
        blogPage.render(resp);

    }

}
