package pl.edu.atar.universityrecruitment;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.logger.KieRuntimeLogger;

public class UniversityMainKnowledgeable {

	public static void main(String[] args) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-rules-knowledgeable");
		
		 // Tworzenie kolekcji faktów
        List<Candidate> uc = new Dataset().getUniversityCandidates();

		// Dodanie do przetwarzania zbioru faktów
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