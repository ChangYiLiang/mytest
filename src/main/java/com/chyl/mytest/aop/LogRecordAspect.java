package com.chyl.mytest.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;

/**
 * @author Tomdy
 * @create 2018-01-10 15:20
 */
@Aspect
@Configuration
public class LogRecordAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);

    private static final String METHOD_NAME = "test";

    //&& execution(* com.fujfu.fubaodai.*.client.*Client.*(..))
    // 定义切点Pointcut
    @Pointcut(value = "execution(* com.chyl.mytest.controller.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String uri = request.getRequestURI();
        logger.info("[" + request.getAttribute("userId") + "] uri ===>>> " + uri);

        MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        if (METHOD_NAME.equals(targetMethod.getName())) {
            Object result = pjp.proceed();
            logger.info("response ===>>> " + JSON.toJSONString(result));
            return result;
        }
        StringBuilder responseStrBuilder = new StringBuilder();
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("request ===>>> " + JSON.toJSONString(responseStrBuilder.toString()));
        Object result = pjp.proceed();
        logger.info("response ===>>> " + JSON.toJSONString(result));
        return result;
    }
}
