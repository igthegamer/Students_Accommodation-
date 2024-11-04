package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        this.reservationService = new ReservationServiceImpl(reservationRepository);
    }

    @Test
    void retrieveAllReservations() {
        Reservation reservation1 = new Reservation("res1", LocalDate.of(2023, 9, 1), true, false, null);
        Reservation reservation2 = new Reservation("res2", LocalDate.of(2023, 9, 2), false, true, null);

        when(reservationRepository.findAll()).thenReturn(Arrays.asList(reservation1, reservation2));

        List<Reservation> reservations = reservationService.retrieveAllReservations();

        assertEquals(2, reservations.size());
        assertEquals("res1", reservations.get(0).getIdReservation());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void retrieveReservation() {
        String reservationId = "res1";
        Reservation mockReservation = new Reservation(reservationId, LocalDate.of(2023, 9, 1), true, false, null);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(mockReservation));

        Reservation reservation = reservationService.retrieveReservation(reservationId);

        assertNotNull(reservation);
        assertEquals("res1", reservation.getIdReservation());
        verify(reservationRepository, times(1)).findById(reservationId);
    }

    @Test
    void addReservation() {
        Reservation newReservation = new Reservation("res3", LocalDate.of(2023, 9, 3), true, false, null);
        when(reservationRepository.save(newReservation)).thenReturn(newReservation);

        Reservation savedReservation = reservationService.addReservation(newReservation);

        assertEquals("res3", savedReservation.getIdReservation());
        verify(reservationRepository, times(1)).save(newReservation);
    }

    @Test
    void modifyReservation() {
        Reservation existingReservation = new Reservation("res1", LocalDate.of(2023, 9, 1), true, false, null);
        when(reservationRepository.save(existingReservation)).thenReturn(existingReservation);

        Reservation modifiedReservation = reservationService.modifyReservation(existingReservation);

        assertEquals("res1", modifiedReservation.getIdReservation());
        verify(reservationRepository, times(1)).save(existingReservation);
    }

    @Test
    void removeReservation() {
        String reservationId = "res1";

        reservationService.removeReservation(reservationId);

        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    void trouverResSelonDateEtStatus() {
        LocalDate date = LocalDate.of(2023, 9, 1);
        boolean status = true;
        Reservation reservation = new Reservation("res1", date, status, false, null);

        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(date, status))
                .thenReturn(Arrays.asList(reservation));

        List<Reservation> foundReservations = reservationService.trouverResSelonDateEtStatus(Date.valueOf(date).toLocalDate(), status);

        assertEquals(1, foundReservations.size());
        assertEquals("res1", foundReservations.get(0).getIdReservation());
        verify(reservationRepository, times(1)).findAllByAnneeUniversitaireBeforeAndEstValide(date, status);
    }

    @Test
    void findReservationsByPaymentStatus() {
        boolean hasPaid = true;
        Reservation reservation = new Reservation("res1", LocalDate.of(2023, 9, 1), true, hasPaid, null);

        when(reservationRepository.findAllByHasPaid(hasPaid)).thenReturn(Arrays.asList(reservation));

        List<Reservation> foundReservations = reservationService.findReservationsByPaymentStatus(hasPaid);

        assertEquals(1, foundReservations.size());
        assertEquals("res1", foundReservations.get(0).getIdReservation());
        verify(reservationRepository, times(1)).findAllByHasPaid(hasPaid);
    }

    @Test
    void updatePaymentStatus() {
        String reservationId = "res1";
        boolean newPaymentStatus = true;
        Reservation mockReservation = new Reservation(reservationId, LocalDate.of(2023, 9, 1), true, false, null);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(mockReservation));
        when(reservationRepository.save(mockReservation)).thenReturn(mockReservation);

        Reservation updatedReservation = reservationService.updatePaymentStatus(reservationId, newPaymentStatus);

        assertEquals(newPaymentStatus, updatedReservation.isHasPaid());
        verify(reservationRepository, times(1)).save(mockReservation);
    }
}
