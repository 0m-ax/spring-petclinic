package org.springframework.samples.petclinic.medicine;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicineController.class)
public class MedicineControllerTests {
    @MockBean
    private PetRepository petRepository;
    @MockBean
    private MedicineRepository medicineRepository;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setup() {
        PetType petType = new PetType();
        petType.setId(2);
        petType.setName("dog");
        given(this.petRepository.findPetTypes()).willReturn(Lists.newArrayList(petType));
        Medicine medicine = new Medicine();
        medicine.setId(3);
        medicine.setName("test");
        medicine.setPetType(petType);
        medicine.setBatchNumber(1);
        medicine.setExpiryDate(LocalDate.of(1998,11,7));
        given(this.medicineRepository.findById(3)).willReturn(medicine);
    }
    @Test
    void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/medicine/new"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("medicine"))
            .andExpect(view().name("medicine/createOrUpdateMedicineForm"));
    }
    @Test
    void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post("/medicine/new")
            .param("name", "test")
            .param("type", "dog")
            .param("expiryDate", "2015-02-12")
            .param("batchNumber", "1")
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/medicine.html"));
    }

}
