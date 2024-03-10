package ma.enset.hospital.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.enset.hospital.entities.*;
import ma.enset.hospital.repositories.*;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class IHospitalServiceImpl implements IHospitalService{
    private PatientRepository patientRepository;
    private MedecinRepository medecinRepository;
    private RendezVousRepository rendezVousRepository;
    private ConsultationRepository consultationRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Medecin saveMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @Override
    public RendezVous saveRenderVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation) { return consultationRepository.save(consultation);}

    @Override
    public User saveUser(User user) {return userRepository.save(user);}

    @Override
    public Role saveRole(Role role) {return roleRepository.save(role);}

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(roleName);
        if (user.getRoles() != null) {
            user.getRoles().add(role);
            role.getUsers().add(user);
        }
    }

    @Override
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new RuntimeException("Invalid user or password");
        if (user.getPassword().equals(password)) {
            return user;
        }
        throw new RuntimeException("Invalid user or password");
    }
}
