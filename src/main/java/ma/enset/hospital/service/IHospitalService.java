package ma.enset.hospital.service;
import ma.enset.hospital.entities.*;

public interface IHospitalService {
    Patient savePatient(Patient patient);
    Medecin saveMedecin(Medecin medecin);
    RendezVous saveRenderVous(RendezVous rendezVous);
    Consultation saveConsultation(Consultation consultation);
    User saveUser(User user);
    Role saveRole(Role role);
    User findUserByUserName(String username);
    Role findRoleByRoleName(String roleName);
    void addRoleToUser(String username, String roleName);
    User authenticate(String username, String password);
}
