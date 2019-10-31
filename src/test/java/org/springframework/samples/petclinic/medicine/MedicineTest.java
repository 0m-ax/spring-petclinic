package org.springframework.samples.petclinic.medicine;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.util.SerializationUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class MedicineTest {
    @Mock
    PetType petType;
    @Test
    void testSerialization() {
        Medicine medicine = new Medicine();
        medicine.setName("ketamin");
        medicine.setBatchNumber(100);
        medicine.setId(123);
        medicine.setPetType(petType);
        Medicine other = (Medicine) SerializationUtils
            .deserialize(SerializationUtils.serialize(medicine));
        assertThat(other.getName()).isEqualTo(medicine.getName());
        assertThat(other.getBatchNumber()).isEqualTo(medicine.getBatchNumber());
        assertThat(other.getId()).isEqualTo(medicine.getId());
    }
}
