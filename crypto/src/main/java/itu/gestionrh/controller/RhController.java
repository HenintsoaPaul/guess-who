package itu.gestionrh.controller;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import itu.gestionrh.model.Contract;
//importation of the model 
import itu.gestionrh.model.Employe;

//Service 
import itu.gestionrh.service.*;
//importation of the repository 
import itu.gestionrh.repository.ContractRepository;
import itu.gestionrh.repository.EmployeRepository;
import itu.gestionrh.repository.TerminationTypeRepository;
import itu.gestionrh.repository.TerminationRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class RhController {
    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private TerminationTypeRepository terminationTypeRepository;

    @Autowired
    private TerminationRepository terminationRepository;

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    EmployeService employeService;

    // for the home
    @GetMapping("/PreHomeRh")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("admin/index");
        view.addObject("active", "home");
        return view;
    }

    // for the employe
    @GetMapping("/PreEmploye")
    public ModelAndView employe() {
        ModelAndView view = new ModelAndView("admin/employe");
        view.addObject("liste_employe", contractRepository.valideContract());
        view.addObject("active", "employe");
        return view;
    }

    // for the page
    @GetMapping("/PreInfEmploye")
    public ModelAndView preinfemploye(@RequestParam("idContract") String idContract) {
        ModelAndView view = new ModelAndView("admin/employeinformation");
        view.addObject("contract", contractRepository.getContractIdContract(idContract));
        view.addObject("active", "employe");
        return view;
    }

    // for the deconnexion
    @GetMapping("/Deconnexion")
    public ModelAndView deconnexion() {
        return new ModelAndView("index");
    }

    // for the rupture
    @GetMapping("/rupture")
    public ModelAndView getMethodName(@RequestParam("idEmp") String idEmp, HttpSession session) {
        ModelAndView view = new ModelAndView("admin/rupture");
        view.addObject("active", "employe");
        view.addObject("type_ruptures", terminationTypeRepository.findAll());
        view.addObject("idEmp", idEmp);
        return view;
    }

    @PostMapping("/ruptureConfirmation")
    public ModelAndView ruptureConfirmation(HttpServletRequest request, HttpSession session) {
        ModelAndView view = new ModelAndView("redirect:/PreHomeRh");

        //
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Get all the value from the formular
        String terminationNotice = request.getParameter("termination_notice");
        String terminationDate = request.getParameter("termination_date");
        String idTerminationType = request.getParameter("id_termination_type");
        String idEmp = request.getParameter("idEmp");

        // Get the other value
        String id_contract = (contractRepository.contractEmploye(idEmp)).getIdContract();
        String id_employe = idEmp;

        //Conversion 
        LocalDate noticeDate = LocalDate.parse(terminationNotice, formatter);
        LocalDate date_final = LocalDate.parse(terminationDate, formatter);

        // insert the value into the termination
        terminationRepository.insertValue(noticeDate, date_final, id_contract, id_employe,
                idTerminationType);

        view.addObject("active", "home");
        return view;
    }

    // @PostMapping("/FichePaie")
    // public ModelAndView generatePaySlip(@RequestParam String employeId,
    //                                     @RequestParam String dateDebut,
    //                                     @RequestParam String dateFin) {
    //     ModelAndView mav = new ModelAndView("paySlip");
    //     Employe employe = employeRepository.employeId(employeId);
    //     Contract contract = contractRepository.contractEmploye(employeId);
        
    //     List<PayrollDetails> details = payrollService.calculatePayroll(employeId, dateDebut, dateFin);

    //     mav.addObject("nomPrenom", employe.getName() + " " + employe.getFirstName());
    //     mav.addObject("matricule", employe.getIdEmploye());
    //     mav.addObject("fonction",contract.getRole().getTitle());
    //     // mav.addObject("cnaps", employe.getCnaps());
    //     mav.addObject("dateEmbauche", employe.getEmployementDate());
    //     mav.addObject("anciennete", employeService.calculateAnciennete(contract.getStartDate()));
    //     mav.addObject("details", details);
    //     mav.addObject("salaireBrut", payrollService.calculateBrut(details));
    //     mav.addObject("totalRetenues", payrollService.calculateRetenues(details));
    //     mav.addObject("netAPayer", payrollService.calculateNet(details));

    //     return mav;
    // }

    @GetMapping("/PreFichePaie")
    public ModelAndView selectEmployee() {
        ModelAndView mav = new ModelAndView("employe/prefichePaie");
        List<Contract> employes = contractRepository.valideContract();
        mav.addObject("employes", employes);
        return mav;
    }
    

}
