package cn.promore.bf.interceptor;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PermissionsInterceptor {
	@Pointcut("execution(* cn.promore.bf.action.*(..))")  
    private void anyMethod(){}//定义一个切入点  

	@Before("anyMethod()")  
    public void doAccessCheck(){  
        System.out.println("前置通知");  
    }  
}
