package com.glanway.jty.interceptor;

import com.glanway.jty.annotation.Token;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token. class );
            if (annotation != null ) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    saveToken(request);
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (!isTokenValid(request)) {
                        response.reset();
                        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    }
                    request.getSession( false ).removeAttribute( "TOKEN" );
                }
            }
            return true ;
        } else {
            return super.preHandle(request, response, handler);
        }
    }



    public String generateToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            byte id[] = session.getId().getBytes();
            byte now[] = new Long(System.currentTimeMillis()).toString().getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(id);
            md.update(now);
            return this.toHex(md.digest());
        } catch (IllegalStateException e) {
            return null;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    public String toHex(byte buffer[]) {
        StringBuffer sb = new StringBuffer();
        String s = null;
        for (int i = 0; i < buffer.length; i++) {
            s = Integer.toHexString((int) buffer[i] & 0xff);
            if (s.length() < 2) {
                sb.append('0');
            }
            sb.append(s);
        }
        return sb.toString();
    }

    public synchronized void saveToken(HttpServletRequest request) {


        HttpSession session = request.getSession();
        String token = generateToken(request);
        if (token != null) {

            session.setAttribute("TOKEN", token);

        }
    }

    public synchronized boolean isTokenValid(HttpServletRequest request) {
        return this.isTokenValid(request, false);
    }

    public synchronized boolean isTokenValid(
            HttpServletRequest request,
            boolean reset) {
        // Retrieve the current session for this request
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        // Retrieve the transaction token from this session, and
        // reset it if requested
        String saved = (String) session.getAttribute("TOKEN");
        if (saved == null) {
            return false;
        }
        if (reset) {
            this.resetToken(request);
        }
        // Retrieve the transaction token included in this request
        String token = request.getParameter("TOKEN");
        if (token == null) {
            return false;
        }
        return saved.equals(token);

    }

    public synchronized void resetToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute("TOKEN");
    }
}