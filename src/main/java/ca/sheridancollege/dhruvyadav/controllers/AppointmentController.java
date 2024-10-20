package ca.sheridancollege.dhruvyadav.controllers;

import ca.sheridancollege.dhruvyadav.beans.Appointment;
import ca.sheridancollege.dhruvyadav.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppointmentController {

    @Autowired
    private DatabaseAccess databaseAccess;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("appointments", databaseAccess.getAllAppointments());
        model.addAttribute("appointment", new Appointment());
        return "index";
    }

    @PostMapping("/insertAppointment")
    public String insertAppointment(@ModelAttribute Appointment appointment, Model model) {
        databaseAccess.insertAppointment(appointment);
        return "redirect:/";
    }

    @GetMapping("/editAppointment/{id}")
    public String editAppointment(@PathVariable Long id, Model model) {
        Appointment appointment = databaseAccess.getAppointmentById(id);
        databaseAccess.deleteAppointmentById(id); // Delete the old record before updating
        model.addAttribute("appointment", appointment);
        model.addAttribute("appointments", databaseAccess.getAllAppointments());
        return "index";
    }

    @GetMapping("/deleteAppointment/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        databaseAccess.deleteAppointmentById(id);
        return "redirect:/";
    }
}
