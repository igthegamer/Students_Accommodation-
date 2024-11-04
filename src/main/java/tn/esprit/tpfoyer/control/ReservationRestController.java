package tn.esprit.tpfoyer.control;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationRestController {

    IReservationService reservationService;

    @GetMapping("/retrieve-all-reservations")
    @ResponseBody
    public List<Reservation> getReservations() {
        return reservationService.retrieveAllReservations();
    }

    @GetMapping("/retrieve-reservation/{reservation-id}")
    @ResponseBody
    public Reservation retrieveReservation(@PathVariable("reservation-id") String rId) {
        return reservationService.retrieveReservation(rId);
    }

    @GetMapping("/retrieve-reservation-date-status/{d}/{v}")
    @ResponseBody
    public List<Reservation> retrieveReservationParDateEtStatus(
            @PathVariable("d") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate d,
            @PathVariable("v") boolean b) {
        return reservationService.trouverResSelonDateEtStatus(d, b);
    }

    @PostMapping("/add-reservation")
    @ResponseBody
    public Reservation addReservation(@RequestBody Reservation r) {
        return reservationService.addReservation(r);
    }

    @DeleteMapping("/remove-reservation/{reservation-id}")
    @ResponseBody
    public void removeReservation(@PathVariable("reservation-id") String rId) {
        reservationService.removeReservation(rId);
    }

    @PutMapping("/modify-reservation")
    @ResponseBody
    public Reservation modifyReservation(@RequestBody Reservation r) {
        return reservationService.modifyReservation(r);
    }

    @PostMapping("/reserve-and-pay")
    public String createReservationAndInitiatePayment(
            @RequestParam("idReservation") String idReservation,
            @RequestParam(value = "anneeUniversitaire") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate anneeUniversitaire,
            @RequestParam(value = "estValide", required = false) Boolean estValide,
            RedirectAttributes redirectAttributes) {

        Reservation reservation = new Reservation();
        reservation.setIdReservation(idReservation);
        reservation.setAnneeUniversitaire(anneeUniversitaire);
        reservation.setEstValide(estValide != null);

        Reservation savedReservation = reservationService.addReservation(reservation);

        redirectAttributes.addAttribute("reservationId", savedReservation.getIdReservation());
        return "redirect:/checkout";
    }





    @GetMapping("/add-reservation-form")
    public String showAddReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "add-reservation";
    }

    @GetMapping("/list-reservations")
    public String listReservations(Model model) {
        List<Reservation> reservations = reservationService.retrieveAllReservations();
        model.addAttribute("reservations", reservations);
        return "list-reservations";
    }
}
