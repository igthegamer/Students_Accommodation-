package tn.esprit.tpfoyer.control;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tn.esprit.tpfoyer.entity.ChargeRequest;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;
import tn.esprit.tpfoyer.service.StripeService;

@Controller

public class ChargeController {
    @Autowired
    private StripeService paymentsService;

    @Autowired
    private IReservationService reservationService;
    @PostMapping("/charge")
    public String charge(@ModelAttribute ChargeRequest chargeRequest,
                         @RequestParam("reservationId") String reservationId,
                         Model model) throws StripeException {
        System.out.println("Received reservationId: " + reservationId);
        if (reservationId == null || reservationId.isEmpty()) {
            model.addAttribute("error", "Reservation ID is missing.");
            return "error";
        }
        if (chargeRequest.getAmount() <= 0) {
            model.addAttribute("error", "Invalid amount");
            return "error";
        }

        Charge charge = paymentsService.charge(chargeRequest);

        if ("succeeded".equals(charge.getStatus())) {
            Reservation reservation = reservationService.retrieveReservation(reservationId);

            if (reservation == null) {
                model.addAttribute("error", "Reservation not found");
                return "error";
            }


            reservation.setHasPaid(true);
            reservationService.modifyReservation(reservation);


            System.out.println("Reservation updated to paid: " + reservationId);
        }


        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
    }



    @GetMapping("/charge/test")
    public String testEndpoint() {
        return "result";
    }

}