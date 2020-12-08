package serwisAudio.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import serwisAudio.model.*;
import serwisAudio.service.ProductForSaleService;
import serwisAudio.service.RepairService;
import serwisAudio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller     // klasa mapująca url na wywołanie metody i zwracająca widok html
public class MainController {
    private UserService userService;
    private RepairService repairService;
    private ProductForSaleService productForSaleService;

    @Autowired
    public MainController(UserService userService, RepairService repairService, ProductForSaleService productForSaleService) {
        this.userService = userService;
        this.repairService = repairService;
        this.productForSaleService = productForSaleService;
    }

    @GetMapping("/")        // na adresie localhost:8080/
    public String home(
            Model model,
            Authentication auth    // można wydobyć dane logowania gdy nie jest null
    ) {   // wywołaj metodę home()
        // dodaje atrybut do obiektu model, który może być przekazany do widoku
        // model.addAttribute(nazwaAtrybutu, wartość);
        model.addAttribute("products", productForSaleService.getAllProductsForSale(0));    // pierwsze 5 postów
        model.addAttribute("auth", userService.getCredentials(auth));
        model.addAttribute("pagesIndexes", productForSaleService.generatePagesIndexes(productForSaleService.getAllProductsForSale()));
        model.addAttribute("pageIndex", 1);
        return "index";     // zwracającą nazwę dokumentu html który ma być wyświetlany
    }
    @GetMapping("/todo")
    public String todoView(Model model, Authentication auth){
        model.addAttribute("auth", userService.getCredentials(auth));
        model.addAttribute("repairs", repairService.getAllRepairs(0));    // pierwsze 5 postów
        model.addAttribute("pagesIndexes", repairService.generatePagesIndexes(repairService.getAllRepairs()));
        model.addAttribute("pageIndex", 1);
        return "todo";
    }
    @GetMapping("/page={pageIndex}")
    public String home(
            @PathVariable("pageIndex") int pageIndex,
            Model model,
            Authentication auth
    ){
        model.addAttribute("repairs", productForSaleService.getAllProductsForSale(pageIndex - 1));
        model.addAttribute("auth", userService.getCredentials(auth));
        model.addAttribute("pagesIndexes", productForSaleService.generatePagesIndexes(productForSaleService.getAllProductsForSale()));
        model.addAttribute("pageIndex", pageIndex);
        return "index";
    }

    @GetMapping("/repairs&{repairId}")
    public String getRepair(
            @PathVariable("repairId") int repairId, Model model, Authentication auth
    ) {
        Optional<Repair> repairOptional = repairService.getRepairById(repairId);
        repairOptional.ifPresent(repair -> model.addAttribute("repair", repair));
        model.addAttribute("auth", userService.getCredentials(auth));
        return "repair";
    }

    @GetMapping("/addRepair")                 // przejście metodą GET na stronę formularze
    public String addRepair(Model model, Authentication auth) {     // i przekazanie pustego obiektu Post
        model.addAttribute("repairDto", new RepairDto());
        model.addAttribute("auth", userService.getCredentials(auth));
        return "addRepair";                   // tu znajduje się formularz i jest uzupłeniany przez użytkownika
        // gdy wprowadza pola do formularza to set-uje pola klasy Post
    }

    @PostMapping("/addRepair")                // przekazanie parametrów z formularza metodą POST
    public String addRepair(
            @Valid                                // zwraca błędy walidacji obiektu PostDto
            @ModelAttribute RepairDto repairDto,      // obiekt model przekazuje obiekt post do metody

            BindingResult bindingResult,          // obiekt zawierający błędy walidacji
            Model model,
            Authentication auth
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.toString()));
            return "addRepair";               // gdy są błędy walidacji to wyświetl z powrotem formularz i nic nie zapisuj
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String loggedEmail = userDetails.getUsername();         // adres email z pobrany z danych logowania
        // userDetails.getAuthorities().forEach(System.out::println);
        // zapisanie nowego posta do db
        repairService.addRepair(repairDto.getSerial(), repairDto.getBrand(), repairDto.getModel(), repairDto.getUserFailDescription(),
                repairDto.getAdditionalInfo(), userService.getUserByEmail(loggedEmail).get(), repairDto.getRepairImage());  // przypisanie dodawanego posta do zalogowanego użytkownika
        return "redirect:/";                // przekierowuje na ades, który zwraca jakiś widok
    }

    @GetMapping("/register")
    public String addUser(Model model, Authentication auth) {
        model.addAttribute("userDto", new UserDto());
        model.addAttribute("auth", userService.getCredentials(auth));
        return "addUser";
    }

    @PostMapping("/register")
    public String addUser(
            @Valid @ModelAttribute UserDto userDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "addUser";
        }
        if (userService.getUserByEmail(userDto.getEmail()).isPresent()) {     // istnieje już taki email
            model.addAttribute("emailError", "e-mail address is not unique");
            return "addUser";
        }
        userService.registerUser(new User(userDto.getEmail(), userDto.getPassword(),
                LocalDateTime.now(), true));
        return "redirect:/";
    }

    @GetMapping("/login")       // adres zwracający frmularz logowania
    public String login(Model model, Authentication auth) {
        model.addAttribute("auth", userService.getCredentials(auth));
        return "login";         // zwrócenie szablonu widoku o nazwie login.html
    }

    @GetMapping("/login&error={loginError}")    // adres zwracający formularz logowania gdy wystąpiły błędy logowania
    public String login(@PathVariable("loginError") Boolean loginError, Model model,
                        Authentication auth) {
        System.out.println(loginError.getClass());
        model.addAttribute("loginError", loginError);
        model.addAttribute("auth", userService.getCredentials(auth));
        return "login";
    }

    @GetMapping("/deleteRepair&{repairId}")
    public String deleteRepair(@PathVariable("repairId") int repairId, Model model, Authentication auth) {
        if (repairService.getRepairById(repairId).isPresent()) {
            repairService.deleteRepairById(repairId);
            return "redirect:/";
        }
        model.addAttribute("errorMessage", "Delete action aborted! There is not repair with id = " + repairId);
        model.addAttribute("repairs", repairService.getAllRepairs());
        model.addAttribute("auth", userService.getCredentials(auth));
        return "index";
    }

    @GetMapping("/editRepair&{repairId}")
    public String updateRepair(
            @PathVariable("repairId") Integer repairId, Model model, Authentication auth) {
        if (repairService.getRepairById(repairId).isPresent()) {
            Repair repairToUpdate = repairService.getRepairById(repairId).get();
            RepairDto repairDto = new RepairDto(
                    repairToUpdate.getSerial(), repairToUpdate.getBrand(), repairToUpdate.getModel(), repairToUpdate.getUserFailDescription(),
                    repairToUpdate.getAdditionalInfo(), repairToUpdate.getRepairImage());

            model.addAttribute("repairDto", repairDto);
            model.addAttribute("repairId", repairId);
            model.addAttribute("auth", userService.getCredentials(auth));
            return "addRepair";
        }
        model.addAttribute("errorMessage", "Update action aborted! There is not repair with id = " + repairId);
        model.addAttribute("repairs", repairService.getAllRepairs());
        model.addAttribute("auth", userService.getCredentials(auth));
        return "index";     // gdy nie ma posta o określonym id przekierowujemy na stronę domową
    }

    @PostMapping("/editRepair&{repairId}")
    public String updateRepair(
            @PathVariable("repairId") int repairId,
            @Valid @ModelAttribute("repairDto") RepairDto repairDto,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            // model.addAttribute("", new ArrayList<>(Arrays.asList(Category.values())));
            return "addRepair";
        }
        if (repairService.editRepair(repairId, repairDto)) {
            return "redirect:/repairs&" + repairId;
        }
        return "redirect:/";
    }



}
