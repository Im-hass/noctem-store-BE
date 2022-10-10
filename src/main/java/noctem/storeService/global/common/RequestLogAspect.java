package noctem.storeService.global.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import noctem.storeService.global.security.bean.ClientInfoLoader;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RequestLogAspect {
    private final ClientInfoLoader clientInfoLoader;

    @Around("execution(* noctem.storeService.store..*Controller.*(..))")
    public Object requestControllerLog(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object target = joinPoint.getTarget();
        if (clientInfoLoader.isAnonymous() == true) {
            log.info("[ANONYMOUS] {} {} {}", request.getMethod(), request.getRequestURI(), target.getClass().getSimpleName());
        } else {
            log.info("[{} {}] {} {}", clientInfoLoader.getUserAccountId(), clientInfoLoader.getUserNickname(), request.getMethod(), request.getRequestURI());
        }
        return joinPoint.proceed();
    }
}
