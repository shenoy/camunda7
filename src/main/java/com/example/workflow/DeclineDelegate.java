package com.example.workflow;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DeclineDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("Decline application");
        boolean x = (boolean) delegateExecution.getVariable("isApproved");
        System.out.println(" x variable in decline ---> " + x);
    }
}
