package com.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/asyncComplete", asyncSupported = true)
public class AsyncCompleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setAttribute("mainThread",Thread.currentThread().getName());
        PrintWriter writer = resp.getWriter();
        writer.println("<html><head><title>Async Servlet</title></head>");
        writer.println("<body><div id='progress'></div>");
        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(60000);
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
//                updateProgress1(writer, asyncContext);
                for (int i = 0; i < 10; i++) {

                    String info = i * 10 + "% progress";
//                    req.setAttribute("progress", info);

//                    try {
//                        req.getRequestDispatcher("/progress.jsp").forward(req, resp);
//                    } catch (ServletException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }

                    writer.println(info);
                    writer.flush();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

//                req.setAttribute("progress","DONE");
//                try {
//                    req.getRequestDispatcher("/progress.jsp").forward(req, resp);
//                } catch (ServletException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                writer.println("DONE");
                asyncContext.complete();
            }
        });
    }

    private void updateProgress1(PrintWriter writer, AsyncContext asyncContext) {
        System.out.println("new thread: " + Thread.currentThread());
        for (int i = 0; i < 10; i++) {
            writer.println("<script>");
            writer.println("document.getElementById('progress').innerHTML='" + (i * 10) + "% complete'");
            writer.println("</script>");
            writer.flush();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        writer.println("<script>");
        writer.println("document.getElementById('progress').innerHTML='DONE'");
        writer.println("</script>");
        writer.println("</body></html>");
        asyncContext.complete();
    }
}
