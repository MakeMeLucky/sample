package org.example.application.bpmn.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "messageFlow")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageFlow extends BPMNElementId {

    @XmlAttribute
    private String sourceRef;

    @XmlAttribute
    private String targetRef;

}
