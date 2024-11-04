package tn.esprit.tpfoyer.control;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.ChargeRequest;

@Controller
public class PaymentController {

    @Value("${stripe.api.publicKey}")
    private String stripePublicKey;

    @GetMapping("/checkout")
    public String checkoutForm(@RequestParam("reservationId") String reservationId, Model model) {
        model.addAttribute("reservationId", reservationId);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("amount") int amount,
                           @RequestParam("currency") ChargeRequest.Currency currency,
                           @RequestParam("reservationId") String reservationId,
                           Model model) {
        model.addAttribute("amount", amount );
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", currency);
        model.addAttribute("reservationId", reservationId);
        System.out.println("reservation"+reservationId);
        return "checkout";
    }

}
