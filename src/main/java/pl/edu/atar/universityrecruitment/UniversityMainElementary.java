package pl.edu.atar.universityrecruitment;

import java.util.List;

import org.kie.api.runtime.*;
import org.kie.api.KieServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniversityMainElementary {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityMainElementary.class);

    public static void main(String[] args) {

        // Tworzenie kolekcji faktów
        List<Candidate> uc = new Dataset().getUniversityCandidates();

        // ZESTAW REGUŁ BARDZO PROSTYCH
        // HOW-TO :: Uruchomienie przykładu.
        // Każdy przykład (Example 1 - Example 4) należy uruchamiać niezależnie.
        // W celu uruchomienia określonego przykładu należy ustawić wartość zmiennej example.
        // Rezultaty działania silnika wnioskującego są zwracane w oknie konsoli oraz zapisywane w plikach w katalogu logs.

        // Uruchamiany przykład
        int example = 1;

        // Logowanie zebranych informacji
        int counter = 1;
        for (Candidate fact : uc) {

            // Tworzenie bazy wiedzy tj. dodawanie zbioru reguł do pamięci produkcyjnej Production Memory
            KieServices kService = KieServices.Factory.get();
            KieContainer kContainer = kService.getKieClasspathContainer();
            KieSession kSession = kContainer.newKieSession("ksession-rules-elementary");

            if(counter == 1) System.out.println("\n\nREASONINGS AND FACTS ANALYSIS\n");

            // Dodanie przetwarzanego faktu do pamięci roboczej Working Memory
            kSession.insert(fact);

            switch (example) {
                case 1:
                    // Set 1. Example 1.
                    // OPIS: Uruchamianie 1 zbioru składającego się z 3 wykluczających się reguł
                    // kwalifikacyjnych.
                    // UWAGA: W celu wykonania testu niniejszego przykładu należy w zbiorze
                    // reguł DoNotFocusAutomatically_OlympicQualification.drl odkomentować n/w wiersz:
                    // agenda-group "do_not_focus_automatically"
                    kSession.getAgenda().getAgendaGroup("one_set_of_rules").setFocus();
                    break;
                case 2:
                    // Set 1. Example 2.
                    // OPIS: Uruchamianie 1 zbioru składającego się z 3 wykluczających się reguł
                    // kwalifikacyjnych oraz zbiorów reguł bez określonej agenda-group tj. z agendy 'MAIN'.
                    // UWAGA: W celu wykonania testu niniejszego przykładu należy w zbiorze
                    // reguł DoNotFocusAutomatically_OlympicQualification.drl zakomentować n/w wiersz:
                    // agenda-group "do_not_focus_automatically"
                    // Po przeprowadzeniu testów należy odkomentować w/w wiersz.
                    kSession.getAgenda().getAgendaGroup("one_set_of_rules").setFocus();
                    break;
                case 3:
                    // Set 1. Example 3.
                    // OPIS: Uruchamianie 1 zbioru składającego się z 3 wykluczających się reguł
                    // kwalifikacyjnych z MODFIKACJĄ faktu powodującą uruchomienie ponownego
                    // wnioskowania.
                    // PYTANIE: W jakim celu używany jest counter?
                    kSession.getAgenda().getAgendaGroup("one_set_of_rules_MODIFY").setFocus();
                    break;
                case 4:
                    // Set 1. Example 4.
                    // OPIS: Uruchamianie 1 zbioru składającego się z 3 wykluczających się reguł
                    // kwalifikacyjnych z MODFIKACJĄ faktu powodującą uruchomienie ponownego
                    // wnioskowania
                    // oraz klauzulą no-loop uniemożliwiającą ponowne uruchomienie reguły przez samą
                    // siebie w przypadku ponownego wnioskowania
                    kSession.getAgenda().getAgendaGroup("one_set_of_rules_no-loop_MODIFY").setFocus();
                    break;
            }

            LOGGER.info("Number of facts in Working Memory (Entry Point): {}", kSession.getFactCount());
            kService.getLoggers().newConsoleLogger(kSession);
            kService.getLoggers().newFileLogger(kSession, "./logs/reasoning_verysimple_fact_" + counter);

            LOGGER.info("Reasoning.");
            kSession.fireAllRules();

            LOGGER.info("Number of facts in Working Memory (Exit Point): {}", kSession.getFactCount());

            LOGGER.info(fact.getCandidateInformationLogger());

            // Usunięcie sesji oraz zwolnienie pamięci.
            kSession.dispose();
            counter++;
        }
    }
}