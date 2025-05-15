package pl.edu.atar.universityrecruitment;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniversityMainBasic {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityMainBasic.class);

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
        for (Candidate candidate : uc) {

            // Tworzenie bazy wiedzy tj. dodawanie zbioru reguł do pamięci produkcyjnej Production Memory
            KieServices kService = KieServices.Factory.get();
            KieContainer kContainer = kService.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("basic");

            if(counter == 1)
                LOGGER.info("Knowledge base created.\n\n");
                //System.out.println("\nKnowledge base created.\n\nREASONINGS AND FACTS ANALYSIS\n");

            // Dodanie przetwarzanego faktu do pamięci roboczej Working Memory
            kSession.insert(candidate);

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

            LOGGER.info("REASONING AND ANALYSIS OF CANDIDATE ID = {}", candidate.getId());
            LOGGER.info("Number of facts in Working Memory (Entry Point): {}", kSession.getFactCount());
            kService.getLoggers().newConsoleLogger(kSession);
            kService.getLoggers().newFileLogger(kSession, "./logs/reasoning_basic_fact_" + counter);

            LOGGER.info("Reasoning No 1.");
            kSession.fireAllRules();

            // Uruchomienie kolejnych wnioskowań, w celu pokazania, że fakt
            // który został przetworzony w pierwszym procesie wnioskowania,
            // nie jest przetwarzany ponownie w kolejnych wywoływanych wnioskowaniach.
            LOGGER.info("Reasoning No 2.");
            kSession.fireAllRules();
            LOGGER.info("Reasoning No 3.");
            kSession.fireAllRules();

            LOGGER.info("Number of facts in Working Memory (Exit Point): {}", kSession.getFactCount());

            LOGGER.info("Information AFTER reasoning (below):\n\n{}\n", candidate.getCandidateInformationLogger());

            // Usunięcie sesji oraz zwolnienie pamięci.
            kSession.dispose();
            counter++;
        }
    }
}