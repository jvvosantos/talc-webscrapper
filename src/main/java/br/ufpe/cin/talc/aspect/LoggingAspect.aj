package br.ufpe.cin.talc.aspect;

public aspect LoggingAspect {
	
	pointcut allMethods(): call(public void br.ufpe.cin.talc.scrapping.Repository*.*(..));
    
    before(): allMethods() {
        System.out.println(thisJoinPoint.getSignature()+ " started");
    }
    
    after(): allMethods() {
        System.out.println(thisJoinPoint.getSignature()+ " ended");
    }
}