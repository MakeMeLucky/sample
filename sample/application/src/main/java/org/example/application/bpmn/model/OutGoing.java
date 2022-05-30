package org.example.application.bpmn.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@Data
@XmlRootElement(name = "outgoing")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutGoing {

    @XmlValue
    private String value;

}