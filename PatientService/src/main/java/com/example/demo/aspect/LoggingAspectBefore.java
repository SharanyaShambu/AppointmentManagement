package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspectBefore {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before("execution(* com.example.demo.service.PatientServiceImpl.*(..))")
	public void logBeforeAllPatientServiceMethods(JoinPoint joinPoint) {
		logger.info(
				"****LoggingAspectBefore.logBeforeAllPatientServiceMethods() : " + joinPoint.getSignature().getName());
	}

	@Before("execution(* com.example.demo.service.PatientServiceImpl.addPatient(..))")
	public void logBeforeAddPatient(JoinPoint joinPoint) {
		logger.info("****LoggingAspectBefore.logBeforeAddPatient() : " + joinPoint.getSignature().getName());
	}

	@Before("execution(* com.example.demo.service.PatientServiceImpl.updatePatientDetails(..))")
	public void logBeforeUpdatePatientDetails(JoinPoint joinPoint) {
		logger.info("****LoggingAspectBefore.logBeforeUpdatePatientDetails() : " + joinPoint.getSignature().getName());
	}

	@Before("execution(* com.example.demo.service.PatientServiceImpl.deletePatient(..))")
	public void logBeforeDeletePatient(JoinPoint joinPoint) {
		logger.info("****LoggingAspectBefore.logBeforeDeletePatient() : " + joinPoint.getSignature().getName());
	}

	@Before("execution(* com.example.demo.service.PatientServiceImpl.save(..))")
	public void logBeforeSave(JoinPoint joinPoint) {
		logger.info("****LoggingAspectBefore.logBeforeSave() : " + joinPoint.getSignature().getName());
	}

	@Before("execution(* com.example.demo.service.PatientServiceImpl.findByPatientId(..))")
	public void logBeforeFindByPatientId(JoinPoint joinPoint) {
		logger.info("****LoggingAspectBefore.logBeforeFindByPatientId() : " + joinPoint.getSignature().getName());
	}

	@Before("execution(* com.example.demo.service.PatientServiceImpl.findById(..))")
	public void logBeforeFindById(JoinPoint joinPoint) {
		logger.info("****LoggingAspectBefore.logBeforeFindById() : " + joinPoint.getSignature().getName());
	}

	@Before("execution(* com.example.demo.service.PatientServiceImpl.show(..))")
	public void logBeforeShow(JoinPoint joinPoint) {
		logger.info("****LoggingAspectBefore.logBeforeShow() : " + joinPoint.getSignature().getName());
	}
}
