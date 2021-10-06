package com.test;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cj
 * @date 2019/12/25
 */
@WebServlet("/put")
public class PutServletTest extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("put----");
        String value = req.getParameter("a");
        System.out.println("-----put debug: value = " + value);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post-----");
        String value = req.getParameter("a");
        System.out.println("-----post debug: value = " + value);
    }
}
