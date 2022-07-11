package servlets;

import controller.PostController;
import exception.NotFoundException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";
    public static final String PATH = "/api/posts";
    public static final String PATH_WITH_ARGUMENTS = PATH + "/";
    private PostController controller;

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext("controller", "service", "repository");
        this.controller = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            if (method.equals(GET) && path.equals(PATH)) {
                controller.all(resp);
            } else if (method.equals(GET) && path.contains(PATH_WITH_ARGUMENTS)) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.getById(id, resp);
            } else if (method.equals(POST) && path.equals(PATH)) {
                controller.save(req.getReader(), resp);
            } else if (method.equals(DELETE) && path.contains(PATH_WITH_ARGUMENTS)) {
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NotFoundException | NumberFormatException exception) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
