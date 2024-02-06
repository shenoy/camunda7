package com.example.workflow;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class HelloWorld implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Printing hello world");
        boolean isApproved = false; // Your boolean expression here
        execution.setVariable("isApproved", isApproved);
        String pid = execution.getProcessInstanceId();

        RuntimeService runtimeService = ProcessEngines.getDefaultProcessEngine().getRuntimeService();
        runtimeService.createMessageCorrelation("modify").setVariable("pid", pid).correlate();
        System.out.println("in hello delegate..pid is." + pid);

    }
}
