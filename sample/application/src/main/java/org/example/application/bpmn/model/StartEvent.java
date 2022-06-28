package org.example.application.bpmn.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@XmlRootElement(name = "startEvent")
@XmlAccessorType(XmlAccessType.FIELD)
public class StartEvent extends BPMNElementId {

    @XmlElement(name = "outgoing")
    private OutGoing outGoing;

    @XmlElement(name = "incoming")
    private Incoming incoming;

    @XmlElement(name = "messageEventDefinition")
    private MessageEventDefinition messageEventDefinition;

}
