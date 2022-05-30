package org.example.application.bpmn.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "process")
@XmlAccessorType(XmlAccessType.FIELD)
public class Process {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String isExecutable;

    @XmlElement(name = "startEvent")
    private List<StartEvent> events;

    @XmlElement(name = "dataObjectReference")
    private List<DataObjectReference> dataObjectReferences;

    @XmlElement(name = "dataObject")
    private List<DataObject> dataObjects;

    @XmlElement(name = "sequenceFlow")
    private List<SequenceFlow> sequenceFlows;

    @XmlElement(name = "task")
    private List<Task> tasks;

    @XmlElement(name = "endEvent")
    private List<EndEvent> endEvents;

    @XmlElement(name = "exclusiveGateway")
    private List<ExclusiveGateway> exclusiveGateways;
}
