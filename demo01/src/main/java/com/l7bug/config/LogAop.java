package com.l7bug.config;


import com.l7bug.DiffFieldUtils;
import com.l7bug.model.LogModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Asp
 *
 * @author Administrator
 * @since 2025/4/15 10:49
 */
@Slf4j
@Aspect
@Component("la")
public class LogAop {
    private final static ThreadLocal<Map<String, Object>> LOG_THREAD_LOCAL = ThreadLocal.withInitial(LinkedHashMap::new);
    private final BeanFactoryResolver resolver;
    private final ExpressionParser parser = new SpelExpressionParser();

    public LogAop(ApplicationContext context) {
        this.resolver = new BeanFactoryResolver(context);
    }

    public static void putLogObject(String key, Object value) {
        Map<String, Object> stringObjectMap = LOG_THREAD_LOCAL.get();
        stringObjectMap.put(key, value);
        LOG_THREAD_LOCAL.set(stringObjectMap);
    }

    @Pointcut("@annotation(com.l7bug.config.SaveLog)")
    public void pointcut() {
    }

    @AfterReturning(value = "@annotation(saveLog)", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result, SaveLog saveLog) {
        log.info("###########开始记录业务操作日志###########");
        LogModel logModel = new LogModel();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(resolver);
        context.setVariable("result", result);
        context.setVariable("args", joinPoint.getArgs());
        context.setVariable("method", joinPoint.getSignature().getName());

        Map<String, Object> stringObjectMap = LOG_THREAD_LOCAL.get();
        context.setVariables(stringObjectMap);
        LOG_THREAD_LOCAL.remove();

        logModel.setContent(Optional.ofNullable(parser.parseExpression(saveLog.content()).getValue(context)).map(Object::toString).orElse(""));
        logModel.setId(Optional.ofNullable(parser.parseExpression(saveLog.id()).getValue(context)).map(Object::toString).orElse(""));
        logModel.setType(Optional.ofNullable(parser.parseExpression(saveLog.type()).getValue(context)).map(Object::toString).orElse(""));
        log.info(logModel.getContent());
        log.info(logModel.getId());
        log.info(logModel.getType());
        try {
            context.setVariable("logObj", logModel);
            log.info("日志保存结果:{}", Objects.requireNonNull(parser.parseExpression(saveLog.saveMethod()).getValue(context)));
            // 记录日志
            log.info("###########结束记录业务操作日志###########");
        } catch (Exception e) {
            log.error("###############记录业务日志异常###############", e);
        }
    }

    public boolean saveLog(LogModel s) {
        if (s == null) {
            return false;
        }
        log.info("记录日志:{}", s);
        return true;
    }

    public String diffFieldMsg(Object oldValue, Object newValue) {
        List<DiffField.Info> infos = DiffFieldUtils.diffFields(oldValue, newValue);
        return infos.stream()
                .map(item -> "%s[%s]改为->[%s]".formatted(item.fieldName(), item.oldValue(), item.newValue()))
                .collect(Collectors.joining(","));
    }

    public String username() {
        return System.currentTimeMillis() % 2 == 0 ? "admin" : "system";
    }
}
