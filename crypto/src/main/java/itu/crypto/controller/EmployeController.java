package itu.crypto.controller;

import itu.crypto.model.LeaveRequest;
import itu.crypto.repository.LeaveRequestRepository;
import itu.crypto.repository.TypeLeaveRepository;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
public class EmployeController {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private TypeLeaveRepository typeLeaveRequestRepository;

    // Injection via le constructeur
    public EmployeController(LeaveRequestRepository leaveRequestRepository, 
                             TypeLeaveRepository typeLeaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.typeLeaveRequestRepository = typeLeaveRequestRepository;
    }

    @PostMapping("/demande-conge")
    public LeaveRequest requestLeave(@RequestBody LeaveRequest leaveRequest) {
        return leaveRequestRepository.save(leaveRequest);
    }

    @GetMapping("/leave-requests/{idEmploye}")
    public List<LeaveRequest> getLeaveRequests(@PathVariable String idEmploye) {
        return leaveRequestRepository.findByEmploye_IdEmploye(idEmploye);
    }

    @GetMapping("/PreHomeEmploye")
    public ModelAndView preHomeEmploye() {
        ModelAndView view = new ModelAndView("employe/index");
        view.addObject("active", "home");
        return view;
    }

    @GetMapping("/PreInformationEmploye")
    public ModelAndView preInformationEmploye(HttpSession session) {
        ModelAndView view = new ModelAndView("employe/information");
        view.addObject("emp", session.getAttribute("emp"));
        view.addObject("active", "information");
        return view;
    }

    @GetMapping("/PreCongeEmploye")
    public ModelAndView preCongeEmploye(HttpSession session) {
        ModelAndView view = new ModelAndView("employe/conge");
        view.addObject("typeleave", typeLeaveRequestRepository.findAll());
        view.addObject("active", "conge");
        return view;
    }

}
