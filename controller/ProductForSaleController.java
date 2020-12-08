package serwisAudio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import serwisAudio.model.ProductForSaleDto;
import serwisAudio.service.ProductForSaleService;
import serwisAudio.service.UserService;

import javax.validation.Valid;

@Controller
public class ProductForSaleController {
    private ProductForSaleService productForSaleService;
    private UserService userService;

    @Autowired
    public ProductForSaleController(ProductForSaleService productForSaleService, UserService userService){
        this.productForSaleService = productForSaleService;
        this.userService = userService;
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model, Authentication auth){
        if(auth == null){
            return "login";
        }
        model.addAttribute("productForSaleDto", new ProductForSaleDto());
        model.addAttribute("auth", userService.getCredentials(auth));
        return "addProduct";
    }

    @PostMapping("/addProduct")                // przekazanie parametrów z formularza metodą POST
    public String addPart(
            @Valid                                // zwraca błędy walidacji obiektu PostDto
            @ModelAttribute ProductForSaleDto productForSaleDto,      // obiekt model przekazuje obiekt post do metody

            BindingResult bindingResult,          // obiekt zawierający błędy walidacji
            Model model,
            Authentication auth
    ) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.toString()));
            return "addProduct";               // gdy są błędy walidacji to wyświetl z powrotem formularz i nic nie zapisuj
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String loggedEmail = userDetails.getUsername();         // adres email z pobrany z danych logowania
        // userDetails.getAuthorities().forEach(System.out::println);
        // zapisanie nowego posta do db
        productForSaleService.addProductForSale(productForSaleDto.getSerial(), productForSaleDto.getBrand(),
                productForSaleDto.getModel());
        return "redirect:/";                // przekierowuje na ades, który zwraca jakiś widok
    }
}
