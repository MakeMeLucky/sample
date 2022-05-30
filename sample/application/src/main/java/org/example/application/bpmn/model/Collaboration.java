package org.example.application.bpmn.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlRootElement(name = "collaboration")
@XmlAccessorType(XmlAccessType.FIELD)
public class Collaboration {

    @XmlAttribute
    private String id;

    @XmlElement(name = "participant")
    private List<Participant> participants;

    @XmlElement(name = "messageFlow")
    private List<MessageFlow> messageFlows;

}
