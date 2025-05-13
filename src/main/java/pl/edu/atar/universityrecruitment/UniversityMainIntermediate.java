package pl.edu.atar.universityrecruitment;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.logger.KieRuntimeLogger;

import java.util.ArrayList;
import java.util.List;

public class UniversityMainIntermediate {

	public static void main(String[] args) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-rules-intermediate");
		
		Candidate uc1 = new Candidate(1L, "Anna", "Kowalewska", 120.0, Boolean.FALSE, "Informatyka", Boolean.TRUE, "Female", "Grammar");
		Candidate uc2 = new Candidate(2L, "Jacek", "Nowak", 110.0, Boolean.FALSE, "Informatyka", Boolean.FALSE, "Male", "Technical");
		Candidate uc3 = new Candidate(3L, "Ewa", "Wiśniowa", 35.0, Boolean.FALSE, "Elektrotechnika", Boolean.FALSE, "Female", "Technical");
		Candidate uc4 = new Candidate(4L, "Karol", "Gruszka", 135.0, Boolean.FALSE, "Automatyka i robotyka", Boolean.TRUE, "Male", "Technical");
		Candidate uc5 = new Candidate(5L, "Kinga", "Poziomka", 30.0, Boolean.FALSE, "Elektrotechnika", Boolean.TRUE, "Female", "Grammar");

		// Utworzenie kolekcji 5 faktów
		List<Candidate> uc = new ArrayList<Candidate>();
		uc.add(uc1);
		uc.add(uc2);
		uc.add(uc3);
		uc.add(uc4);
		uc.add(uc5);

		// Dodanie do przetwarzania zbioru 5 faktów
		for(Candidate fact:uc) {
			kSession.insert(fact);
		}

		// ZESTAW REGUŁ ŚREDNIOZAAWANSOWANYCH		
		// HOW-TO :: Uruchomienie przykładu.
		// Każdy przykład (Example 1 - Example 4) należy uruchamiać niezależnie.
		// W celu uruchomienia określonego przykładu należy ustawić wartość zmiennej example. 
		// Rezultaty działania silnika wnioskującego są zwracane w oknie konsoli.

		// Uruchamiany przykład
		Integer example = 3;

		switch (example) {
		case 1:
			// Set 3. Example 1.
			// OPIS: Uruchamianie 2 zbiorów reguł kwalifikacyjnych. Oba zbiory reguł nie
			// wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
			// Reguły z obu zbiorow "ExamQualification" oraz "OlympicQualification" są
			// wykonywane z MODFIKACJĄ faktu powodującą uruchomienie ponownego wnioskowania.
			// Reguły posiadają parametry activation-group oraz salience.
			// PYTANIE: Dlaczego reguły zostały uruchomione 10-krotnie?
			kSession.getAgenda().getAgendaGroup("two_sets_of_rules_salience_activation-group_MODIFY").setFocus();
			break;
		case 2:
			// Set 3. Example 2.
			// OPIS: Uruchamianie 2 zbiorów reguł kwalifikacyjnych. Oba zbiory reguł nie
			// wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
			// Reguły z obu zbiorow "ExamQualification" oraz "OlympicQualification" są
			// wykonywane z MODFIKACJĄ faktu powodującą uruchomienie ponownego wnioskowania.
			// Reguły posiadają parametry activation-group, salience oraz no-loop.
			// PYTANIE: Dlaczego mimo użycia parametru no-loop reguły zostały uruchomione
			// 10-krotnie?
			kSession.getAgenda().getAgendaGroup("two_sets_of_rules_salience_activation-group_no-loop_MODIFY").setFocus();
			break;
		case 3:
			// Set 3. Example 3.
			// OPIS: Uruchamianie 2 zbiorów reguł kwalifikacyjnych. Oba zbiory reguł nie
			// wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
			// Reguły z obu zbiorow "ExamQualification" oraz "OlympicQualification" są
			// wykonywane z MODFIKACJĄ faktu powodującą uruchomienie ponownego wnioskowania.
			// Reguły posiadają parametry activation-group, salience oraz lock-on-active.
			// PYTANIE: Jak działa parametr lock-on-active?
			kSession.getAgenda().getAgendaGroup("two_sets_of_rules_salience_activation-group_lock-on-active_MODIFY").setFocus();
			break;
		}
		
		System.out.println("Number of facts in Working Memory (Entry Point): " + kSession.getFactCount());
		kSession.addEventListener(new DebugAgendaEventListener());
        kSession.addEventListener(new DebugRuleRuntimeEventListener());
		KieRuntimeLogger logger = ks.getLoggers().newFileLogger(kSession, "./rules-logger");
		
		kSession.fireAllRules();
		kSession.dispose();
				
		// Logowanie zebranych informacji
		for(Candidate fact:uc) {
			System.out.println(fact.getCandidateInformation());
		}
	}
}