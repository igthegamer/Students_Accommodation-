package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    ReservationRepository reservationRepository;

    public List<Reservation> retrieveAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation retrieveReservation(String reservationId) {
        return reservationRepository.findById(reservationId).get();
    }

    public Reservation addReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    public Reservation modifyReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> trouverResSelonDateEtStatus(LocalDate d, boolean b) {
        return reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(d, b);
    }

    public void removeReservation(String reservationId) {
        reservationRepository.deleteById(reservationId);
    }
    @Override
    public List<Reservation> findReservationsByPaymentStatus(boolean hasPaid) {
        return reservationRepository.findAllByHasPaid(hasPaid);
    }
    @Override
    public Reservation updatePaymentStatus(String reservationId, boolean hasPaid) {
        Reservation reservation = retrieveReservation(reservationId);
        reservation.setHasPaid(hasPaid);
        return reservationRepository.save(reservation);
    }

}
