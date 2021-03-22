package cz.muni.fi.pa165;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import javax.inject.Named;
import java.lang.reflect.Method;

@Aspect
@Named
public class AspectForDuration {
    @Around("execution(* cz.muni.fi.pa165..*(..))")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;
        long start = System.currentTimeMillis();
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            System.out.println("proceed failed");
            throwable.printStackTrace();
        }
        long end = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> methodClass = method.getDeclaringClass();
        String methodClassName = methodClass.getName();
        String methodName = method.getName();
        long totalTime = end-start;
        String output = "Method named: " + methodName + " from " + methodClassName +" class run for " + totalTime + " milliseconds!";
        System.out.println(output);
        return result;
    }
}

//https://stackoverflow.com/questions/11270459/aspectj-around-pointcut-all-methods-in-java
//https://stackoverflow.com/questions/5714411/getting-the-java-lang-reflect-method-from-a-proceedingjoinpoint