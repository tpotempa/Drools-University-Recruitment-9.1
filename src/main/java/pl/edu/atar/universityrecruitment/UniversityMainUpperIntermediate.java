package pl.edu.atar.universityrecruitment;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.logger.KieRuntimeLogger;

public class UniversityMainUpperIntermediate {

	public static void main(String[] args) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-rules");
		
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

		// ZESTAW REGUŁ ZAAWANSOWANYCH
		// HOW-TO :: Uruchomienie przykładu.
		// Każdy przykład (Example 1 - Example 1) należy uruchamiać niezależnie.
		// W celu uruchomienia określonego przykładu należy ustawić wartość zmiennej example. 
		// Rezultaty działania silnika wnioskującego są zwracane w oknie konsoli.

		// Uruchamiany przykład
		Integer example = 1;
		
		switch (example) {
		case 1:
			// Set 1. Example 1.
			// OPIS: Uruchamianie 3 zbiorów reguł kwalifikacyjnych. Zbiory reguł nie
			// wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
			// PYTANIE: W jakiej kolejności wykonują się reguły?
			kSession.getAgenda().getAgendaGroup("three_sets_of_rules_salience_lock-on-active_bonus_MODIFY").setFocus();
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