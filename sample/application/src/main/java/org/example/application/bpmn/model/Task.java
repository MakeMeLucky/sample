package org.example.application.bpmn.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;

@Getter
@Setter
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task extends BPMNElementId {

    @XmlAttribute
    private String name;

    @XmlElement(name = "incoming")
    private Incoming incoming;

    @XmlElement(name = "outgoing")
    private OutGoing outGoing;

}
