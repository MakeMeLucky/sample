package org.example.application.bpmn.model;


import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;


@Data
@XmlRootElement(name = "definitions")
@XmlAccessorType(XmlAccessType.FIELD)
public class Definitions {

    @XmlAttribute
    private String id;

    @XmlElement(name = "collaboration")
    private Collaboration collaboration;

    @XmlElement(name = "process")
    private List<Process> process;

}
