package org.example.application.bpmn.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "participant")
@XmlAccessorType(XmlAccessType.FIELD)
public class Participant extends BPMNElementId{

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String processRef;

}
