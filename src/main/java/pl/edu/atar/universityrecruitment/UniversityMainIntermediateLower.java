package pl.edu.atar.universityrecruitment;

import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniversityMainIntermediateLower {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityMainIntermediateLower.class);

    public static void main(String[] args) {

        // Tworzenie kolekcji faktów
        List<Candidate> uc = new Dataset().getUniversityCandidates();

        // ZESTAW REGUŁ PROSTYCH
        // HOW-TO :: Uruchomienie przykładu.
        // Każdy przykład (Example 1 - Example 6) należy uruchamiać niezależnie.
        // W celu uruchomienia określonego przykładu należy ustawić wartość zmiennej example.
        // Rezultaty działania silnika wnioskującego są zwracane w oknie konsoli.

        // Uruchamiany przykład
        int example = 1;

        // Logowanie zebranych informacji
        int counter = 1;
        for (Candidate fact : uc) {

            KieServices kService = KieServices.Factory.get();
            KieContainer kContainer = kService.getKieClasspathContainer();
            KieBase kBase = kContainer.getKieBase("intermediate.lower");
            KieSession kSession = kBase.newKieSession();

            if(counter == 1) System.out.println("\n\nREASONINGS AND FACTS ANALYSIS\n");

            // Dodanie przetwarzanego faktu do pamięci roboczej Working Memory
            kSession.insert(fact);

            switch (example) {
                case 1:
                    // Set 2. Example 1.
                    // OPIS: Uruchamianie 2 zbiorów reguł kwalifikacyjnych. Oba zbiory reguł nie
                    // wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
                    kSession.getAgenda().getAgendaGroup("two_sets_of_rules").setFocus();
                    break;
                case 2:
                    // Set 2. Example 2.
                    // OPIS: Uruchamianie 2 zbiorów reguł kwalifikacyjnych. Oba zbiory reguł nie
                    // wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
                    // Reguły sa uruchamiane z parametrem salience, który określa priorytet
                    // kolejności uruchomienia (wyższa wartość to wyższy priorytet).
                    kSession.getAgenda().getAgendaGroup("two_sets_of_rules_salience").setFocus();
                    break;
                case 3:
                    // Set 2. Example 3.
                    // OPIS: Uruchamianie 2 zbiorów reguł kwalifikacyjnych. Oba zbiory reguł nie
                    // wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
                    // Uruchamianie tylko jednej reguły kwalifikacyjnej, gdyż nie jest zasadne
                    // 2-krotna kwalifikacja kandydata, z wykorzystaniem parametru activation-group,
                    // który określa, że gdy dowolna reguła należąca do activation-group zostanie aktywowana,
                    // uruchomienie wszystkich pozostałych, które należą do grupy, jest anulowane.
                    // PYTANIE: Dlaczego nie wszystie fakty tj. kandydaci mają określoną decyzję?
                    kSession.getAgenda().getAgendaGroup("two_sets_of_rules_salience_activation-group").setFocus();
                    break;
                case 4:
                    // Set 2. Example 4.
                    // OPIS: Uruchamianie 2 zbiorów reguł kwalifikacyjnych. Oba zbiory reguł nie
                    // wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
                    // Reguła ze zbioru "OlympicQualification" jest wykonywana z MODFIKACJĄ faktu
                    // powodującą uruchomienie ponownego wnioskowania.
                    // Modyfikacja polega na 2-krotnym zwiększeniu liczby punktów egzaminacyjnych,
                    // uzyskanych przez kandydata będącego finalistą olimipiady.
                    // Należy podkreślić, że kandydat będący finalistą olimpiady nie może zostać przyjęty
                    // bezpośrednio na podstawie olimpiady. Jedyną podstawą kwalifikacji kandydata
                    // jest liczba egzaminacyjnych.
                    // Reguła "ExamQualification" jest także wykonywana z modyfikacją faktu.
                    // Reguły posiadają parametry no-loop oraz salience.
                    kSession.getAgenda().getAgendaGroup("two_sets_of_rules_salience_no-loop_MODIFY").setFocus();
                    break;
                case 5:
                    // Set 2. Example 5.
                    // OPIS: Uruchamianie 2 zbiorów reguł kwalifikacyjnych. Oba zbiory reguł nie
                    // wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
                    // Reguła ze zbioru "OlympicQualification" jest wykonywana z MODFIKACJĄ faktu
                    // powodującą uruchomienie ponownego wnioskowania.
                    // Modyfikacja polega na 2-krotnym zwiększeniu liczby punktów egzaminacyjnych,
                    // uzyskanych przez kandydata będącego finalistą olimipiady.
                    // Należy podkreślić, że kandydat będący finalistą olimpiady nie może zostać przyjęty
                    // bezpośrednio na podstawie olimpiady. Jedyną podstawą kwalifikacji kandydata
                    // jest liczba egzaminacyjnych.
                    // Reguła "ExamQualification" jest wykonywana nez modyfikacji faktu.
                    // Reguły posiadają parametry no-loop oraz salience.
                    kSession.getAgenda().getAgendaGroup("two_sets_of_rules_salience_no-loop_ONE_MODIFY").setFocus();
                    break;
                case 6:
                    // Set 2. Example 6.
                    // OPIS: Uruchamianie 2 zbiorów reguł kwalifikacyjnych. Oba zbiory reguł nie
                    // wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
                    // Reguła ze zbioru "OlympicQualification" jest wykonywana z MODFIKACJĄ faktu
                    // powodującą uruchomienie ponownego wnioskowania.
                    // Modyfikacja polega na 2-krotnym zwiększeniu liczby punktów egzaminacyjnych,
                    // uzyskanych przez kandydata będącego finalistą olimipiady.
                    // Należy podkreślić, że kandydat będący finalistą olimpiady nie może zostać przyjęty
                    // bezpośrednio na podstawie olimpiady. Jedyną podstawą kwalifikacji kandydata
                    // jest liczba egzaminacyjnych.
                    // Reguła "ExamQualification" jest także wykonywana z modyfikacją faktu.
                    // Reguły posiadają parametry lock-on-active oraz salience.
                    kSession.getAgenda().getAgendaGroup("two_sets_of_rules_salience_lock-on-active_MODIFY").setFocus();
                    break;
            }

            LOGGER.info("Number of facts in Working Memory (Entry Point): {}", kSession.getFactCount());
            kService.getLoggers().newConsoleLogger(kSession);
            kService.getLoggers().newFileLogger(kSession, "./logs/reasoning_simple_fact_" + counter);

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