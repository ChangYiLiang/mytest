package com.chyl.mytest.redis;


import com.chyl.mytest.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


/**
 * @author Tomdy
 * @create 2018-08-15 下午1:53
 */
@Slf4j
@Aspect
@Configuration
public class RedisLockAspect {

    ExpressionParser parser = new SpelExpressionParser();
    LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    @Autowired
    private JedisPool jedisPool;

    @Around(value = "@annotation(RedisLock)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        Object[] arguments = pjp.getArgs();
        String  key = (String) parseSpel(redisLock.key(), method, arguments); // 解析SpEL表达式

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String userId = (String) request.getAttribute("userId");
        Object result = null;
        String methodName = pjp.getSignature().getName();
        //防止重复提交
        Jedis jedis = jedisPool.getResource();
        String requestId = StringUtil.getUUID();//唯一标识
        String lockKey = "fbd_" + methodName + "_" + userId;
        boolean res = false;
        try {
            res = RedisTool.getDistributedLock(jedis, lockKey, requestId);//添加分布式锁
            if (res) {
                result = pjp.proceed(); //执行
            } else {
                log.info("重复提交的请求: " + lockKey);
                return "重复提交的请求";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            if (res) {
                RedisTool.releaseDistributedLock(jedis, lockKey, requestId);////移除分布式锁
                log.info("释放分布式锁成功: " + lockKey);
            }
            jedis.close();
        }
        return result;
    }

    /**
     * 解析SpEL表达式
     * @param key SpEL表达式
     * @param method 反射得到的方法
     * @param args 反射得到的方法参数
     * @return 解析后SpEL表达式对应的值
     */
    private Object parseSpel(String key, Method method, Object[] args){
        // 创建解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 通过Spring的LocalVariableTableParameterNameDiscoverer获取方法参数名列表
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        // 构造上下文
        EvaluationContext context = new StandardEvaluationContext();
        if(args.length == parameterNames.length){
            for (int i = 0,len = args.length; i < len; i++) {
                // 使用setVariable方法来注册自定义变量
                context.setVariable(parameterNames[i], args[i]);
            }
        }
        return parser.parseExpression(key).getValue(context);
    }
}
