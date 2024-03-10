package ma.enset.hospital;

import ma.enset.hospital.entities.*;
import ma.enset.hospital.repositories.ConsultationRepository;
import ma.enset.hospital.repositories.MedecinRepository;
import ma.enset.hospital.repositories.PatientRepository;
import ma.enset.hospital.repositories.RendezVousRepository;
import ma.enset.hospital.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication {
	private static final List<String> namesPatients = List.of(
			"Alice", "Bob", "Charlie", "David", "Eve",
			"Fiona", "George", "Hannah", "Ian", "Julia");

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}

	@Bean
	CommandLineRunner start(IHospitalService hospitalService,
							PatientRepository patientRepository,
							RendezVousRepository rendezVousRepository,
							MedecinRepository medecinRepository){
		return args -> {
			Random random = new Random();

			// CRUD sur les patients

			// Ajouter des patients
			for (int i = 0; i< 50; i++){
				String name = namesPatients.get(random.nextInt(namesPatients.size()));
				Patient patient = new Patient();
				patient.setNom(name);
				patient.setDateNaissance(new Date());
				patient.setMalade(Math.random() > 0.5);
				patient.setScore((int) (Math.random() * 100));
				hospitalService.savePatient(patient);
			}

			// Consulter tous les patients
			Page<Patient> patients = patientRepository.findAll(PageRequest.of(0, 10));
			System.out.println("Total pages : " + patients.getTotalPages());
			System.out.println("Total elements : " + patients.getTotalElements());
			System.out.println("Num page : " + patients.getNumber());
			List<Patient> content = patients.getContent();
			System.out.println("----------------Patients----------------\n");
			content.forEach(patient -> {
				System.out.println("***************************************");
				System.out.println("Id : " + patient.getId());
				System.out.println("Nom : " + patient.getNom());
				System.out.println("Date de naissance : " + patient.getDateNaissance());
				System.out.println("Malade : " + patient.isMalade());
				System.out.println("Score : " + patient.getScore());
			});

			/*
			Chercher des patients
			List<Patient> patientList = patientRepository.chercherPatients("%e%");
			System.out.println("\n------- Patients dont leurs noms contient le caracter e -------");
			patientList.forEach(patient -> {
				System.out.println("***************************************");
				System.out.println("Id : " + patient.getId());
				System.out.println("Nom : " + patient.getNom());
				System.out.println("Date de naissance : " + patient.getDateNaissance());
				System.out.println("Malade : " + patient.isMalade());
				System.out.println("Score : " + patient.getScore());
			});
			*/

			// Consulter un patient
			long idToFind = (long) (Math.random()*100);
			Patient patientToFind = patientRepository.findById(idToFind).orElse(null);
			if (patientToFind != null){
				System.out.println("\nId : " + patientToFind.getId());
				System.out.println("Nom : " + patientToFind.getNom());
				System.out.println("Date de naissance : " + patientToFind.getDateNaissance());
				System.out.println("Malade : " + patientToFind.isMalade());
				System.out.println("Score : " + patientToFind.getScore());
			}

			// Mettre à jour un patient
			long idToUpdate = (long) (Math.random()*100);
			Patient patientToUpdate = patientRepository.findById(idToUpdate).orElse(null);
			if (patientToUpdate != null){
				patientToUpdate.setScore(1000);
				hospitalService.savePatient(patientToUpdate);
				System.out.println("\nPatient num " + idToUpdate + " est bien modifier!");
			}

			// Supprimer un patient
			long idToRemove = (long) (Math.random()*100);
			Patient patientToRemove = patientRepository.findById(idToRemove).orElse(null);
			if (patientToRemove != null){
				patientRepository.deleteById(idToRemove);
				System.out.println("\nPatient num " + idToRemove + " est bien supprimer!");
			}else {
				System.out.println("\nPatient num " + idToRemove + " est deja supprimer!");
			}


			Stream.of("Khalid", "Fatima", "Hanan", "Ahmed").forEach(name -> {
				Medecin medecin = new Medecin();
				medecin.setNom(name);
				medecin.setEmail(name + "@gmail.com");
				medecin.setSpecialite(Math.random() > 0.5 ? "Cardio" : "Dentist");
				hospitalService.saveMedecin(medecin);
			});

			Patient patient = patientRepository.findById(1L).orElse(null);
			//Patient patient1 = patientRepository.findByNom("Bob");
			Medecin medecin = medecinRepository.findByNom("Fatima");

			RendezVous rendezVous = new RendezVous();
			rendezVous.setDate(new Date());
			rendezVous.setStatus(StatusRDV.PENDING);
			rendezVous.setMedecin(medecin);
			rendezVous.setPatient(patient);
			RendezVous saveRDV = rendezVousRepository.save(rendezVous);
			System.out.println("\nRendez-vous num : " + saveRDV.getId());

			RendezVous rendezVous1 = rendezVousRepository.findById(1L).orElse(null);
			Consultation consultation = new Consultation();
			consultation.setDateConsultation(new Date());
			consultation.setRendezVous(rendezVous1);
			consultation.setRapport("Rapport de la premier consultation");
			hospitalService.saveConsultation(consultation);
			System.out.println("\nConsultation num : " + consultation.getId());

		};
	}
}
