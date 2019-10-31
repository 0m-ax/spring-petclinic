package org.springframework.samples.petclinic.medicine;

import org.springframework.samples.petclinic.vet.Vet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Medicines {

    private List<Medicine> medicines;

    @XmlElement
    public List<Medicine> getMedicinesList() {
        if (medicines == null) {
            medicines = new ArrayList<>();
        }
        return medicines;
    }

}
