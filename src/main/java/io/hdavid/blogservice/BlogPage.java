package io.hdavid.blogservice;

import javax.servlet.http.HttpServletResponse;

public interface BlogPage {

    void render(HttpServletResponse resp);
}
