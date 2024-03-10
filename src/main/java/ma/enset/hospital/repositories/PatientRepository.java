package ma.enset.hospital.repositories;

import ma.enset.hospital.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByNom(String nom);
    @Query("select p from Patient p where p.nom LIKE :x")
    List<Patient> chercherPatients(@Param("x") String nom);
}
