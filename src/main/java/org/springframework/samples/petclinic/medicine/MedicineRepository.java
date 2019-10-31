package org.springframework.samples.petclinic.medicine;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

public interface MedicineRepository extends Repository<Medicine, Integer> {
    @Transactional(readOnly = true)
    Collection<Medicine> findAll() throws DataAccessException;
    Medicine findById(int medicineID) throws DataAccessException;
    void save(Medicine medicine);
    Collection<Medicine> findByExpiryDateBefore(LocalDate expiryDate)throws  DataAccessException;
}
