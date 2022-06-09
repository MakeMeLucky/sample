package org.example.application.microservices.converters;

import org.example.application.bpmn.model.*;
import org.example.application.kafka.KafkaService;
import org.example.application.microservices.model.Record;
import org.example.application.services.PostgreComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RecordConverter {

    @Autowired
    PostgreComponent postgreComponent;

    @Autowired
    KafkaService kafkaService;

    private static final Map<String, String> microservicesIds = new HashMap<String, String>() {{
        put("Определить тип приказа","define-order-type");
        put("Отправить отчет и уведомление руководителю о выполнении запроса","send-rpt-notif-head");
        put("Разослать уведомления сотрудникам", "send-notif-empl");
        put("Регистрация списка публикаций", "register-publications-list");
        put("Сформировать список публикаций", "form-list-publications");
        put("Зарегистрировать отчет", "register-report");
        put("Зарегистрировать уведомление", "register-notification");
    }};

    public Record convert(Definitions definitions) {

        // message flows
        List<MessageFlow> messageFlows = definitions.getCollaboration().getMessageFlows();

        // sequence flows
        List<SequenceFlow> sequenceFlows = definitions.getProcess().stream()
                .flatMap(process -> process.getSequenceFlows().stream())
                .collect(Collectors.toList());

        // tasks
        List<Task> tasks = definitions.getProcess().stream()
                .flatMap(process -> process.getTasks().stream())
                .collect(Collectors.toList());

        // <id, task>
        Map<String, Task> taskMap = tasks.stream()
                .collect(Collectors.toMap(Task::getId, Function.identity()));

        // events
        List<StartEvent> events = definitions.getProcess().stream()
                .flatMap(process -> process.getEvents().stream())
                .collect(Collectors.toList());

        // <id, event>
        Map<String, StartEvent> eventMap = events.stream()
                .collect(Collectors.toMap(StartEvent::getId, Function.identity()));

        // step 1 . get first event to initiate process
        StartEvent firstEvent = events.stream()
                .filter(fe->fe.getId().startsWith("StartEvent"))
                .findFirst().orElseThrow(() -> new RuntimeException("No start task"));

        Record record = nextService(sequenceFlows, messageFlows, firstEvent, eventMap, taskMap);

        kafkaService.sendMessage(record.getId(), record);

        return record;
    }

    private Record nextService(List<SequenceFlow> sequenceFlows, List<MessageFlow> messageFlows,StartEvent event,
                               Map<String, StartEvent> eventMap, Map<String, Task> taskMap) {
        Record record = new Record();
        String currentSequenceFlowId = event.getOutGoing().getValue();
        SequenceFlow currentSequenceFlow = sequenceFlows.stream().filter(sf->sf.getId().equals(currentSequenceFlowId)).findFirst().get();
        String taskId = currentSequenceFlow.getTargetRef();
        List<MessageFlow> currentMessageFlows = messageFlows.stream().filter(mf->mf.getSourceRef().equals(taskId)).collect(Collectors.toList());
        List<String> nextEventsIds = currentMessageFlows.stream().map(MessageFlow::getTargetRef).collect(Collectors.toList());
        List<StartEvent> nextEvents = nextEventsIds.stream().map(eventMap::get).collect(Collectors.toList());
        record.setRealName(taskMap.get(taskId).getName());
        record.setId(microservicesIds.get(record.getRealName()));
        for (StartEvent e : nextEvents) {
            record.addRecord(nextService(sequenceFlows, messageFlows, e, eventMap, taskMap));
        }
            return record;
    }

}
