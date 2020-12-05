package serwisAudio.controller;

import serwisAudio.model.Repair;
import serwisAudio.model.User;
import serwisAudio.service.RepairService;
import serwisAudio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

// klasa mapująca żądania prokołu http - adres lokalny http://localhost:8080
//@Controller       //- mapuje żądanie i zwraca widok html
@RequestMapping("/api")     // przed każdym adresem jest prefix /api
@RestController     //- mapuje żądania i dane REST - Representative State Transfer
public class MainRESTController {
    UserService userService;
    RepairService repairService;

    @Autowired              // wstrzykiwanie zależności przez konstturktor
    public MainRESTController(UserService userService, RepairService repairService) {
        this.userService = userService;
        this.repairService = repairService;
    }

    @PostMapping("/user/register")
    public void registerUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        User user = new User(email, password, LocalDateTime.now(), false);
        userService.registerUser(user);
    }

    @PutMapping("/user/registerConfirm")
    public void registerConfirm(
            @RequestParam("userId") int userId
    ) {
        userService.activateUser(userId);
    }

    @DeleteMapping("/user/delete")
    public void deleteUserById(
            @RequestParam("userId") int userId
    ) {
        try {
            userService.deleteUser(userId);
        } catch (EmptyResultDataAccessException e) {

        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsersOrderByRegistrationDateTimeDesc();
    }

   /* @GetMapping("/user/email={email}")
    public User getUserById(
            @RequestParam("email") String email
    ) {
        return userService.getUserByEmail(email).orElse(new User());
    }*/

    /*@PostMapping("/post/addPost")
    public void addPost(
            @RequestParam("serial") String serial,
            @RequestParam("brand") String brand,
            @RequestParam("model") String model,
            @RequestParam("userFailDescription") String userFailDescription,
            @RequestParam("additionalInfo") String additionalInfo,
            @RequestParam("userId") int userId
    ) {
        User user = userService.getUserById(userId).get();
        repairService.addRepair(serial, brand, model, userFailDescription, additionalInfo, user, );

        }*/


    @GetMapping("/repairs")
    public List<Repair> getAllRepairs() {
        return repairService.getAllRepairs();
    }


    // GET      - SELECT - pobiera zawartość z bazy i zwraca obiekt lub listę obiektów
    // POST     - INSERT - wprowadza dane do bazy i nic nie zwraca
    // PUT      - UPDATE - edytuje dane z bazy i nic nie zwraca
    // DELETE   - DELETE - usuwanie obiektu lub listy obiektów z bazy
    @GetMapping("/")                         // localhost:8080/
    public String home() {
        return "Witaj w naszym serwisie";
    }

    @GetMapping("/home/{name}&{status}")
    public String homeWithName(
            @PathVariable("name") String name,
            @PathVariable("status") Boolean status
    ) {
        return status ? "Hello " + name + " in hompage" : "Your account is locked";
    }

    @GetMapping("/credentials")
    public String getCredentials(
            @RequestParam("login") String login,
            @RequestParam("password") String password
    ) {
        return String.format("login : %s \npassword : %s", login, password);
    }
}


