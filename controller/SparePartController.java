package serwisAudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import serwisAudio.model.*;
import serwisAudio.service.SparePartService;
import serwisAudio.service.UserService;

import javax.validation.Valid;
import java.util.EnumSet;
import java.util.List;

@Controller
public class SparePartController {
    private SparePartService sparePartService;
    private UserService userService;

    @Autowired
    public SparePartController(SparePartService sparePartService, UserService userService){
        this.sparePartService = sparePartService;
        this.userService = userService;
    }

    @GetMapping("/addPart")
    public String addPart(Model model, Authentication auth){
        if(auth == null){
            return "login";
        }
        model.addAttribute("sparePartDto", new SparePartDto());
        EnumSet<PartType> allPartTypes = sparePartService.getAllPartTypes();
        model.addAttribute("partTypes", allPartTypes);
        model.addAttribute("auth", userService.getCredentials(auth));
        return "addPart";
    }

    @GetMapping("/showParts")
    public String showParts(Model model, Authentication auth) {
        List<SparePart> spareParts = sparePartService.getAllSpareParts();
        model.addAttribute("spareParts", spareParts);
        model.addAttribute("auth", userService.getCredentials(auth));
        return "partCatalogue";
    }
    @PostMapping("/addPart")                // przekazanie parametrów z formularza metodą POST
    public String addPart(
            @Valid                                // zwraca błędy walidacji obiektu PostDto
            @ModelAttribute SparePartDto sparePartDto,      // obiekt model przekazuje obiekt post do metody

            BindingResult bindingResult,          // obiekt zawierający błędy walidacji
            Model model,
            Authentication auth
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.toString()));
            return "addPart";               // gdy są błędy walidacji to wyświetl z powrotem formularz i nic nie zapisuj
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String loggedEmail = userDetails.getUsername();         // adres email z pobrany z danych logowania
        // userDetails.getAuthorities().forEach(System.out::println);
        // zapisanie nowego posta do db
        sparePartService.addPart(sparePartDto.getSymbol(), sparePartDto.getName(),
                sparePartDto.getQuantity(),sparePartDto.getPrice(), sparePartDto.getPartType());  // przypisanie dodawanego posta do zalogowanego użytkownika
        return "redirect:/showParts";                // przekierowuje na ades, który zwraca jakiś widok
    }
}
