package org.springframework.samples.petclinic.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Controller
class MedicineController {
    @Autowired
    private final MedicineRepository medicineRepository;
    @Autowired
    private final PetRepository petRepository;
    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return this.petRepository.findPetTypes();
    }
    MedicineController(MedicineRepository medicineRepository, PetRepository petRepository) {
        this.medicineRepository = medicineRepository;
        this.petRepository = petRepository;
    }

    @GetMapping("/medicine.html")
    public String showMedicineList(Map<String, Object> model) {
        Medicines medicines = new Medicines();
        medicines.getMedicinesList().addAll(this.medicineRepository.findAll());
        model.put("medicines", medicines);
        return "medicines/medicineList";
    }
    @GetMapping("/medicineExpired.html")
    public String showExpiredMedicineList(Map<String, Object> model) {
        Medicines medicines = new Medicines();
        medicines.getMedicinesList().addAll(this.medicineRepository.findByExpiryDateBefore(LocalDate.now()));
        model.put("medicines", medicines);
        return "medicines/medicineList";
    }
    @GetMapping("/medicine/new")
    public String initCreationForm(ModelMap model) {
        Medicine medicine = new Medicine();
        model.put("medicine", medicine);
        return "medicines/createOrUpdateMedicineForm";
    }
    @GetMapping("/medicine/{medicineId}/edit")
    public String initUpdateForm(@PathVariable("medicineId") int medicineId, ModelMap model) {
        Medicine medicine = this.medicineRepository.findById(medicineId);
        model.put("medicine", medicine);
        return "medicines/createOrUpdateMedicineForm";
    }
    @PostMapping("/medicine/new")
    public String processSaveForm(@Valid Medicine medicine, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.put("medicine", medicine);
            return "medicines/createOrUpdateMedicineForm";
        } else {
            this.medicineRepository.save(medicine);
            return "redirect:/medicine.html";
        }
    }
    @PostMapping("/medicine/{medicineId}/edit")
    public String processUpdateForm(@Valid Medicine medicine, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.put("medicine", medicine);
            return "medicines/createOrUpdateMedicineForm";
        } else {
            this.medicineRepository.save(medicine);
            return "redirect:/medicine.html";
        }
    }
}
