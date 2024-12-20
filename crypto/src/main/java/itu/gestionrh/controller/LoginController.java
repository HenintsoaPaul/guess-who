package itu.gestionrh.controller;

import itu.gestionrh.repository.AccountRepository;
import itu.gestionrh.repository.ContractRepository; // Vous devez ajouter ce repository
import jakarta.servlet.http.HttpSession;
import itu.gestionrh.model.Employe;
import itu.gestionrh.model.Role;
import itu.gestionrh.model.Contract;  // Vous devez avoir ce modèle de Role
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private ContractRepository contractRepository;  // Ajout du RoleRepository pour obtenir le rôle

    @PostMapping
    public ModelAndView login(@RequestParam String matricule, @RequestParam String password, HttpSession session) {
        return accountRepository.findByEmploye_IdEmployeAndPassword(matricule, password)
                .map(account -> {
                    // Trouver le rôle de l'employé après la connexion
                    Employe emp = account.getEmploye();
                    Contract contract = contractRepository.contractEmploye(emp.getIdEmploye()); // Assurez-vous d'avoir cette méthode
                    Role role = contract.getRole();
                    session.setAttribute("emp",emp);
                    if (contract != null && role.getIdRole().equals("R001")) { // Si rôle RH                                                                      
                        return new ModelAndView("redirect:/PreHomeRh"); 
                    } else {
                        return new ModelAndView("redirect:/PreHomeEmploye");
                    }
                })
                .orElse(new ModelAndView("index"));
    }
}
