package org.example.application.bpmn.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "definitions")
@XmlAccessorType(XmlAccessType.FIELD)
public class Definitions extends BPMNElementId {

    @XmlElement(name = "collaboration")
    private Collaboration collaboration;

    @XmlElement(name = "process")
    private List<Process> process;

}
