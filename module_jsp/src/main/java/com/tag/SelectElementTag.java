package com.tag;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class SelectElementTag extends SimpleTagSupport {
    private String[] countries = {"中国","日本","韩国","美国","澳大利亚"};

    @Override
    public void doTag() throws JspException, IOException {
        JspContext jspContext = getJspContext();
        JspWriter out = jspContext.getOut();
        out.print("<select>\n");
        for (int i = 0; i < countries.length; i++) {
            getJspContext().setAttribute("value",countries[i]);
            getJspContext().setAttribute("text",countries[i]);
            getJspBody().invoke(null);
        }
        out.print("</select>\n");
    }
}
