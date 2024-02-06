package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AcceptDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Accepted application");
        boolean x = (boolean) delegateExecution.getVariable("isApproved");
        System.out.println(" x variable in accept ---> " + x);
    }
}
