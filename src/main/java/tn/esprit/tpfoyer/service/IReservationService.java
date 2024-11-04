package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Reservation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface IReservationService {

    public List<Reservation> retrieveAllReservations();
    public Reservation retrieveReservation(String reservationId);
    public Reservation addReservation(Reservation r);
    public void removeReservation(String reservationId);
    public Reservation modifyReservation(Reservation reservation);

    public List<Reservation> trouverResSelonDateEtStatus(LocalDate d, boolean b);
    public List<Reservation> findReservationsByPaymentStatus(boolean hasPaid);

    public Reservation updatePaymentStatus(String reservationId, boolean hasPaid);

}
