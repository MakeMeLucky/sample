package org.example.application.bpmn.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "startEvent")
@XmlAccessorType(XmlAccessType.FIELD)
public class StartEvent {

    @XmlAttribute
    private String id;

    @XmlElement(name = "outgoing")
    private OutGoing outGoing;

    @XmlElement(name = "incoming")
    private Incoming incoming;

    @XmlElement(name = "messageEventDefinition")
    private MessageEventDefinition messageEventDefinition;

}
