package org.example.application.converters;

import org.example.application.bpmn.model.*;
import org.example.application.microservices.ServiceProcess;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ServiceConverter {

    public List<ServiceProcess> convert(Definitions definitions) {

        // from one pool to another
        List<MessageFlow> messageFlows = definitions.getCollaboration().getMessageFlows();

        // in one pool
        List<SequenceFlow> sequenceFlows = definitions.getProcess().stream()
                .flatMap(process -> process.getSequenceFlows().stream())
                .collect(Collectors.toList());

        // tasks
        List<Task> tasks = definitions.getProcess().stream()
                .flatMap(process -> process.getTasks().stream())
                .collect(Collectors.toList());

        // events
        List<StartEvent> events = definitions.getProcess().stream()
                .flatMap(process -> process.getEvents().stream())
                .collect(Collectors.toList());

        List<ServiceProcess> processes = messageFlows.stream()
                .map(messageFlow -> createServiceProcess(messageFlow, sequenceFlows))
                .collect(Collectors.toList());

        return processes;
    }

    private ServiceProcess createServiceProcess(MessageFlow messageFlow, List<SequenceFlow> sequenceFlows) {

        String source = messageFlow.getSourceRef();
        String target = messageFlow.getTargetRef();

        ServiceProcess mainProcess = new ServiceProcess();

        ServiceProcess initProcess = new ServiceProcess(source, target);

        List<ServiceProcess> processes = createInnerProcess(target, sequenceFlows);
        processes.add(0, initProcess);

        mainProcess.setSource(source);
        mainProcess.setTarget(processes.get(processes.size() - 1).getTarget());
        mainProcess.setServiceProcesses(processes);

        return mainProcess;
    }

    private List<ServiceProcess> createInnerProcess(String target, List<SequenceFlow> sequenceFlows) {
        String source = "";
        List<ServiceProcess> serviceProcesses = new ArrayList<>();
        while (!source.equals(target)) {
            Optional<SequenceFlow> sequenceFlow = getSequenceFlow(sequenceFlows, target);
            if (sequenceFlow.isPresent()) {
                SequenceFlow sf = sequenceFlow.get();
                serviceProcesses.add(new ServiceProcess(sf.getSourceRef(), sf.getTargetRef()));
                target = sequenceFlow.get().getTargetRef();
            } else {
                source = target;
            }

        }

        return serviceProcesses;
    }

    private Optional<SequenceFlow> getSequenceFlow(List<SequenceFlow> sequenceFlows, String target) {
        return sequenceFlows.stream().filter(flow -> target.equals(flow.getSourceRef())).findFirst();
    }

}
