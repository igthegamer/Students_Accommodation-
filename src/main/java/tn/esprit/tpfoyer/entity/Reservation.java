package tn.esprit.tpfoyer.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation {

    @Id
    String idReservation;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate anneeUniversitaire;
    boolean estValide;

    boolean hasPaid = false;


    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = (anneeUniversitaire != null) ? formatter.format(anneeUniversitaire) : "null";
        return "Reservation(idReservation=" + idReservation +
                ", anneeUniversitaire=" + formattedDate +
                ", estValide=" + estValide +
                ", hasPaid=" + hasPaid +
                ", etudiants=" + etudiants + ")";
    }







    @ManyToMany
    Set<Etudiant> etudiants;




    /*@ToString.Exclude
    @JsonIgnore*/

}


