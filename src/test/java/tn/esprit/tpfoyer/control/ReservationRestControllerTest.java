package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReservationRestController.class)
class ReservationRestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    IReservationService reservationService;

    @Test
    void getReservationsShouldReturnAllReservations() throws Exception {
        List<Reservation> mockReservations = Arrays.asList(
                new Reservation("res1", LocalDate.of(2023, 9, 1), true, false, null),
                new Reservation("res2", LocalDate.of(2023, 9, 2), false, true, null)
        );

        when(reservationService.retrieveAllReservations()).thenReturn(mockReservations);

        mvc.perform(get("/reservation/retrieve-all-reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReservation").value("res1"))
                .andExpect(jsonPath("$[0].anneeUniversitaire").value("2023-09-01"))
                .andExpect(jsonPath("$[0].estValide").value(true))
                .andExpect(jsonPath("$[0].hasPaid").value(false))
                .andExpect(jsonPath("$[1].idReservation").value("res2"))
                .andExpect(jsonPath("$[1].anneeUniversitaire").value("2023-09-02"))
                .andExpect(jsonPath("$[1].estValide").value(false))
                .andExpect(jsonPath("$[1].hasPaid").value(true));

        verify(reservationService, times(1)).retrieveAllReservations();
    }

    @Test
    void retrieveReservation() throws Exception {
        String reservationId = "res1";
        Reservation mockReservation = new Reservation(reservationId, LocalDate.of(2023, 9, 1), true, false, null);

        when(reservationService.retrieveReservation(reservationId)).thenReturn(mockReservation);

        mvc.perform(get("/reservation/retrieve-reservation/{reservation-id}", reservationId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value(reservationId))
                .andExpect(jsonPath("$.anneeUniversitaire").value("2023-09-01"))
                .andExpect(jsonPath("$.estValide").value(true))
                .andExpect(jsonPath("$.hasPaid").value(false));
    }

    @Test
    void addReservation() throws Exception {
        Reservation newReservation = new Reservation("res3", LocalDate.of(2023, 9, 3), true, false, null);
        when(reservationService.addReservation(any(Reservation.class))).thenReturn(newReservation);

        mvc.perform(post("/reservation/add-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idReservation\":\"res3\",\"anneeUniversitaire\":\"2023-09-03\",\"estValide\":true,\"hasPaid\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("res3"))
                .andExpect(jsonPath("$.anneeUniversitaire").value("2023-09-03"))
                .andExpect(jsonPath("$.estValide").value(true))
                .andExpect(jsonPath("$.hasPaid").value(false));
    }

    @Test
    void removeReservation() throws Exception {
        String reservationId = "res1";

        mvc.perform(delete("/reservation/remove-reservation/{reservation-id}", reservationId))
                .andExpect(status().isOk());

        verify(reservationService, times(1)).removeReservation(reservationId);
    }

    @Test
    void modifyReservation() throws Exception {
        Reservation updatedReservation = new Reservation("res1", LocalDate.of(2023, 9, 1), false, true, null);
        when(reservationService.modifyReservation(any(Reservation.class))).thenReturn(updatedReservation);

        mvc.perform(put("/reservation/modify-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idReservation\":\"res1\",\"anneeUniversitaire\":\"2023-09-01\",\"estValide\":false,\"hasPaid\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("res1"))
                .andExpect(jsonPath("$.anneeUniversitaire").value("2023-09-01"))
                .andExpect(jsonPath("$.estValide").value(false))
                .andExpect(jsonPath("$.hasPaid").value(true));
    }

}
