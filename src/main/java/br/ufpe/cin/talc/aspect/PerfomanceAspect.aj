package br.ufpe.cin.talc.aspect;

public aspect LoggingAspect {
	
	pointcut desiredMethods(): call(public void br.ufpe.cin.talc.scrapping.Repository*.scrap(..));
    
    around(ProceedingJoinPoint joinPoint): desiredMethods() {
        long startTime = System.currentTimeMillis();
	    try {
	      return joinPoint.proceed();
	    }
	    finally {
	      System.out.println(joinPoint + " took -> " + (System.currentTimeMillis() - startTime) / 60000 + " minutes");
	    }
    }
}