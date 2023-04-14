package com.comitfy.crm.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class Interceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

       // final AllowAnnonymous allowAnnonymous = ((HandlerMethod)handler).getMethod().getAnnotation((AllowAnnonymous.class));

       /* if(allowAnnonymous != null){
            return true;
        }*/

        if (request.getHeader("Authorization") == null){
            log.info("Authorization not sent.");
            log.info("Validation NOK.");
            return false;
        }
        else if (request.getHeader("Authorization").equals("Test")) {
            log.info("Validation OK.");
            return true;
        } else {
            log.info("Validation NOK.");
            return false;
        }

    }
}
