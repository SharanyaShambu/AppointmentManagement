package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspectAround {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around("execution(* com.example.demo.service.PatientServiceImpl.*(..))")
	public Object logAroundAllPatientServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("****LoggingAspectAround.logAroundAllPatientServiceMethods() - Before : "
				+ joinPoint.getSignature().getName());
		Object result = joinPoint.proceed();
		logger.info("****LoggingAspectAround.logAroundAllPatientServiceMethods() - After : "
				+ joinPoint.getSignature().getName());
		return result;
	}

	@Around("execution(* com.example.demo.service.PatientServiceImpl.addPatient(..))")
	public Object logAroundAddPatient(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("****LoggingAspectAround.logAroundAddPatient() - Before : " + joinPoint.getSignature().getName());
		Object result = joinPoint.proceed();
		logger.info("****LoggingAspectAround.logAroundAddPatient() - After : " + joinPoint.getSignature().getName());
		return result;
	}

	@Around("execution(* com.example.demo.service.PatientServiceImpl.updatePatientDetails(..))")
	public Object logAroundUpdatePatientDetails(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("****LoggingAspectAround.logAroundUpdatePatientDetails() - Before : "
				+ joinPoint.getSignature().getName());
		Object result = joinPoint.proceed();
		logger.info("****LoggingAspectAround.logAroundUpdatePatientDetails() - After : "
				+ joinPoint.getSignature().getName());
		return result;
	}

	@Around("execution(* com.example.demo.service.PatientServiceImpl.deletePatient(..))")
	public Object logAroundDeletePatient(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info(
				"****LoggingAspectAround.logAroundDeletePatient() - Before : " + joinPoint.getSignature().getName());
		Object result = joinPoint.proceed();
		logger.info("****LoggingAspectAround.logAroundDeletePatient() - After : " + joinPoint.getSignature().getName());
		return result;
	}

	@Around("execution(* com.example.demo.service.PatientServiceImpl.save(..))")
	public Object logAroundSave(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("****LoggingAspectAround.logAroundSave() - Before : " + joinPoint.getSignature().getName());
		Object result = joinPoint.proceed();
		logger.info("****LoggingAspectAround.logAroundSave() - After : " + joinPoint.getSignature().getName());
		return result;
	}

	@Around("execution(* com.example.demo.service.PatientServiceImpl.findByPatientId(..))")
	public Object logAroundFindByPatientId(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info(
				"****LoggingAspectAround.logAroundFindByPatientId() - Before : " + joinPoint.getSignature().getName());
		Object result = joinPoint.proceed();
		logger.info(
				"****LoggingAspectAround.logAroundFindByPatientId() - After : " + joinPoint.getSignature().getName());
		return result;
	}

	@Around("execution(* com.example.demo.service.PatientServiceImpl.findById(..))")
	public Object logAroundFindById(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("****LoggingAspectAround.logAroundFindById() - Before : " + joinPoint.getSignature().getName());
		Object result = joinPoint.proceed();
		logger.info("****LoggingAspectAround.logAroundFindById() - After : " + joinPoint.getSignature().getName());
		return result;
	}

}