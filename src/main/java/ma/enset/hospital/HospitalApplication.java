package ma.enset.hospital;

import ma.enset.hospital.entities.Patient;
import ma.enset.hospital.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}

	@Bean
	CommandLineRunner start(PatientRepository patientRepository){
		return args -> {
			Patient patient = new Patient();
			patient.setNom("Anas");
			patient.setDateNaissance(new Date());
			patient.setMalade(false);
			patient.setScore(0);
			patientRepository.save(patient);
		};
	}
}
