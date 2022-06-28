package org.example.application.bpmn.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "collaboration")
@XmlAccessorType(XmlAccessType.FIELD)
public class Collaboration extends BPMNElementId {

    @XmlElement(name = "participant")
    private List<Participant> participants;

    @XmlElement(name = "messageFlow")
    private List<MessageFlow> messageFlows;

}
