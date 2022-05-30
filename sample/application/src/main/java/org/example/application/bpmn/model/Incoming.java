package org.example.application.bpmn.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@Data
@XmlRootElement(name = "incoming")
@XmlAccessorType(XmlAccessType.FIELD)
public class Incoming {

    @XmlValue
    private String value;

}
