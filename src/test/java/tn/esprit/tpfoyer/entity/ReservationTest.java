package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationTest {

    private final Reservation reservation = new Reservation("res123", LocalDate.of(2023, 9, 1), true, false, new HashSet<>());

    @Test
    void noArgConstructorShouldInitializeWithDefaultValues() {
        Reservation reservationDefault = new Reservation();

        assertThat(reservationDefault.getIdReservation()).isNull();
        assertThat(reservationDefault.getAnneeUniversitaire()).isNull();
        assertThat(reservationDefault.isEstValide()).isFalse();
        assertThat(reservationDefault.isHasPaid()).isFalse();
        assertThat(reservationDefault.getEtudiants()).isNull();
    }

    @Test
    void getIdReservationShouldNotBeNull() {
        assertThat(reservation.getIdReservation()).isNotNull();
        assertThat(reservation.getAnneeUniversitaire()).isEqualTo(LocalDate.of(2023, 9, 1));
        assertThat(reservation.isEstValide()).isTrue();
        assertThat(reservation.isHasPaid()).isFalse();
        assertThat(reservation.getEtudiants()).isEmpty();
    }

    @Test
    void toStringShouldBeTested() {
        String expectedToString = "Reservation(idReservation=res123, anneeUniversitaire=2023-09-01, estValide=true, hasPaid=false, etudiants=[])";

        assertThat(reservation.toString()).isEqualTo(expectedToString);
    }
}
