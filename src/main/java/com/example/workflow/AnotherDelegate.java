package com.example.workflow;

import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.feel.syntaxtree.In;

public class AnotherDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        RuntimeService runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
        String pid = (String) delegateExecution.getVariable("pid");
        System.out.println("--------------In the another delegate------------pid is --" + pid);
        runtimeService.createProcessInstanceModification(pid)
            .startBeforeActivity("accept")
            .cancelAllForActivity("decline")
            .execute();

    }
}
