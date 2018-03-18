package com.ly.demo.domain.handle;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Soloist on 2018/3/19 0:09
 */
public class BaseExceptionHandler extends AbstractHandlerExceptionResolver implements InitializingBean {
    
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        
    }
}
