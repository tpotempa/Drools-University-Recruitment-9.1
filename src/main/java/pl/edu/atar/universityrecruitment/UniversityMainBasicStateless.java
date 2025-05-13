package pl.edu.atar.universityrecruitment;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UniversityMainBasicStateless {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityMainBasicStateless.class);

    public static void main(String[] args) {

        // Tworzenie kolekcji faktów
        List<Candidate> uc = new Dataset().getUniversityCandidates();

        // ZESTAW REGUŁ ELEMENTARNYCH
        // HOW-TO :: Uruchomienie przykładu.
        // Każdy przykład (Example 1 - Example 2) należy uruchamiać niezależnie.
        // W celu uruchomienia określonego przykładu należy ustawić wartość zmiennej example.
        // Rezultaty działania silnika wnioskującego są zwracane w oknie konsoli.

        // Uruchamiany przykład
        int example = 1;

        // Logowanie zebranych informacji
        int counter = 1;
        for (Candidate fact : uc) {

            // Tworzenie bazy wiedzy tj. dodawanie zbioru reguł do pamięci produkcyjnej Production Memory
		    KieServices kService = KieServices.Factory.get();
		    KieContainer kContainer = kService.getKieClasspathContainer();
		    StatelessKieSession kSession = kContainer.newStatelessKieSession("ksession-rules-basic-stateless");

            if(counter == 1) System.out.println("\n\nSTATELESS REASONINGS AND FACTS ANALYSIS\n");

            // Dodanie przetwarzanego faktu do pamięci roboczej Working Memory

            switch (example) {
                case 1:
                    // Set 1. Example 1.
                    // OPIS: Uruchamianie zbiorów reguł z określoną agenda-group.
                    // Agenda grupuje reguły w nazwane zbiory pozwalając sterować,
                    // które zbiory reguł mają być podczas wnioskowania przetwarzane.
                    // UWAGA: W celu wykonania testu niniejszego przykładu należy w zbiorze
                    // reguł DoNotFocusAutomatically_OlympicQualification.drl sprawdzić czy jest
                    // odkomentowany n/w wiersz:
                    // agenda-group "do_not_focus_automatically"
                    break;
                case 2:
                    // Set 1. Example 2.
                    // OPIS: Uruchamianie zbiorów reguł bez określonej agenda-group.
                    // Agenda grupuje reguły w nazwane zbiory pozwalając sterować,
                    // które zbiory reguł mają być podczas wnioskowania przetwarzane.
                    // Reguły bez określonej agenda-group są niejawnie przyporządkowane
                    // do domyślnej agendy 'MAIN'! Agenda 'MAIN' jest w procesie wnioskowanie
                    // ZAWSZE wywoływana tj. uzyskuje FOCUS.
                    // Wywołanie jest niejawne i odbywa się po jawnych wywołaniach agend.
                    // UWAGA: W celu wykonania testu niniejszego przykładu należy w zbiorze
                    // reguł DoNotFocusAutomatically_OlympicQualification.drl zakomentować n/w wiersz:
                    // agenda-group "do_not_focus_automatically"
                    // Po przeprowadzeniu testów należy odkomentować w/w wiersz.
                    break;
            }

            kService.getLoggers().newConsoleLogger(kSession);
            kService.getLoggers().newFileLogger(kSession, "./logs/reasoning_basic_stateless_fact_" + counter);

            // Wnioskowanie z użyciem przekazanego faktu
            kSession.execute(fact);

            LOGGER.info(fact.getCandidateInformationLogger());

            counter++;
        }
    }
}