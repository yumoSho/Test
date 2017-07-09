package com.glanway.jty.servlet.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

import static com.glanway.jty.servlet.jsp.EscapeXmlELResolver.ESCAPE_XML_ATTRIBUTE;
import static com.glanway.jty.utils.HtmlCleaner.*;

/**
 * @author vacoor
 */
public class SafeHtmlTag extends BodyTagSupport {
    private String whitelist;

    @Override
    public int doStartTag() throws JspException {
        pageContext.setAttribute(ESCAPE_XML_ATTRIBUTE, false);
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            String content = getBodyContent().getString();

            if ("none".equals(whitelist)) {
                content = none(content);
            } else if ("basic".equals(whitelist)) {
                content = basic(content);
            } else if ("basic-with-images".equals(whitelist)) {
                content = basicWithImages(content);
            } else if ("simple-text".equals(whitelist)) {
                content = simpleText(content);
            } else if (null == whitelist || "relaxed".equals(whitelist)) {
                content = relaxed(content);
            } else {
                throw new JspTagException("invalid whitelist: " + whitelist + ", whitelist allowed value: none, basic, basic-with-images, simple-text, relaxed");
            }

            pageContext.getOut().write(content);
            return super.doEndTag();
        } catch (IOException e) {
            throw new JspException(e);
        } finally {
            pageContext.removeAttribute(ESCAPE_XML_ATTRIBUTE);
        }
    }

    @Override
    public void release() {
        whitelist = null;
        super.release();
    }

    public String getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(String whitelist) {
        this.whitelist = whitelist;
    }
}
