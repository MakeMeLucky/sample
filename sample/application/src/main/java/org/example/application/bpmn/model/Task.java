package org.example.application.bpmn.model;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String name;

    @XmlElement(name = "incoming")
    private Incoming incoming;

    @XmlElement(name = "outgoing")
    private OutGoing outGoing;

}
