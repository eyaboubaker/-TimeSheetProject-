package tn.esprit.spring.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;



@Component
@Aspect
public class LoggingAspect {

	private static final Logger l = LogManager.getLogger(LoggingAspect.class);
	

	@Before("execution(* tn.esprit.spring.services.EmployeServiceImpl.*(..))")
	public void logMethodEntry(JoinPoint joinPoint) {
	String name = joinPoint.getSignature().getName();
	l.log(Level.INFO,"Entrée dans le méthode :{0} ",name );
	}
	
	@Around("execution(* tn.esprit.spring.services.EmployeServiceImpl.*(..))")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
	long start = System.currentTimeMillis();
	Object obj = pjp.proceed();
	long elapsedTime = System.currentTimeMillis() - start;	
	l.log(Level.INFO, "temps d'éxécution est  %s milliseconds. ", elapsedTime);  
	return obj;
	}
	
	@After("execution(* tn.esprit.spring.services.EmployeServiceImpl.*(..))")
	public void logMethodExit(JoinPoint joinPoint)
	{
	String name = joinPoint.getSignature().getName();
	
	l.log(Level.INFO, "Sortie de la méthode :  {0} ", name);  
	}
	
}
