package org.example.application.bpmn.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "dataObject")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataObject {

    @XmlAttribute
    private String id;
}
