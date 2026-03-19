package com.openclassrooms.safetynet.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.record.ChildAlertResponse;
import com.openclassrooms.safetynet.record.PersonResponse;
import com.openclassrooms.safetynet.util.AgeCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service gérant la logique métier pour l'endpoint /childAlert.
 * Permet de récupérer les enfants habitant à une adresse donnée
 * ainsi que les autres membres de leur foyer.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ChildAlertService {

    private final IDataService dataService;

    /**
     * Calcule l'âge d'une personne à partir de son dossier médical.
     * Retourne 0 si aucun dossier médical n'est trouvé.
     *
     * @param p la personne dont on veut calculer l'âge
     * @return l'âge en années
     */
    private int getAge(Person p) {
        return AgeCalculator.calculateAge(
            dataService.getMedicalRecords().stream()
                .filter(m -> m.firstName().equals(p.firstName()) &&
                             m.lastName().equals(p.lastName()))
                .findFirst()
                .map(m -> m.birthdate())
                .orElse("01/01/1900")
        );
    }

    /**
     * Retourne la liste des enfants (18 ans ou moins) habitant à l'adresse donnée,
     * accompagnés des autres membres du foyer.
     *
     * @param address l'adresse à rechercher
     * @return liste de {@link ChildAlertResponse}, vide si aucun enfant trouvé
     */
    public List<ChildAlertResponse> getChildrenByAddress(String address) {
        log.debug("Getting children for address {}", address);

        List<Person> residents = dataService.getPersons().stream()
                .filter(p -> p.address().equals(address))
                .toList();

        return residents.stream()
                .filter(p -> getAge(p) <= 18)
                .map(p -> {
                    List<PersonResponse> household = residents.stream()
                            .filter(o -> !o.equals(p))
                            .map(o -> new PersonResponse(o.firstName(), o.lastName(), o.address(), o.phone()))
                            .toList();
                    return new ChildAlertResponse(p.firstName(), p.lastName(), getAge(p), household);
                })
                .toList();
    }
}