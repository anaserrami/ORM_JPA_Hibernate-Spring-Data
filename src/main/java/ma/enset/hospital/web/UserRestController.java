package ma.enset.hospital.web;

import jakarta.websocket.server.PathParam;
import ma.enset.hospital.entities.User;
import ma.enset.hospital.repositories.UserRepository;
import ma.enset.hospital.service.IHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/users/{username}")
    public User user(@PathVariable String username){
        return userRepository.findByUsername(username);
    }
}
