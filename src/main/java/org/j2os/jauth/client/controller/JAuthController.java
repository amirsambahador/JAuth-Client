package org.j2os.jauth.client.controller;

import org.j2os.jauth.client.api.JAuthAPI;
import org.j2os.jauth.entity.Role;
import org.j2os.jauth.entity.User;
import org.springframework.stereotype.Controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class JAuthController implements Filter {
    static {
        System.out.println();
        System.out.println();
        System.out.println("     ██╗ █████╗ ██╗   ██╗████████╗██╗  ██╗\n" +
                           "     ██║██╔══██╗██║   ██║╚══██╔══╝██║  ██║\n" +
                           "     ██║███████║██║   ██║   ██║   ███████║\n" +
                           "██   ██║██╔══██║██║   ██║   ██║   ██╔══██║\n" +
                           "╚█████╔╝██║  ██║╚██████╔╝   ██║   ██║  ██║\n" +
                           " ╚════╝ ╚═╝  ╚═╝ ╚═════╝    ╚═╝   ╚═╝  ╚═╝");
        System.out.println();
        System.out.println();
    }
    private static String server;
    private static String token;

    public static void setToken(String token) {
        JAuthController.token = token;
    }

    public static void setServer(String server) {
        JAuthController.server = server;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        JAuthAPI jAuthToken = new JAuthAPI(server, token);
        try {
            User user = (User) jAuthToken.findByToken(request.getParameter("jauth-token"));
            List<String> roleNames = user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
            if (roleNames.contains(request.getRequestURI()))
                filterChain.doFilter(servletRequest, servletResponse);
            else
                response.sendError(401);
        } catch (Exception e) {
            response.sendError(401);
        }
    }

}
