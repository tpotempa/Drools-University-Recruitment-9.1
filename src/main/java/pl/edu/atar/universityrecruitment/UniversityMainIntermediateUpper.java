package pl.edu.atar.universityrecruitment;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.logger.KieRuntimeLogger;

import java.util.ArrayList;
import java.util.List;

public class UniversityMainIntermediateUpper {

	public static void main(String[] args) {

		KieServices kService = KieServices.Factory.get();
	    KieContainer kContainer = kService.getKieClasspathContainer();
        KieBase kBase = kContainer.getKieBase("intermediate.upper");
        KieSession kSession = kBase.newKieSession();

        // Tworzenie kolekcji faktów
        List<Candidate> uc = new Dataset().getUniversityCandidates();

		// Dodanie do przetwarzania zbioru faktów
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
		KieRuntimeLogger logger = kService.getLoggers().newFileLogger(kSession, "./rules-logger");
		
		kSession.fireAllRules();
		kSession.dispose();
				
		// Logowanie zebranych informacji
		for(Candidate fact:uc) {
			System.out.println(fact.getCandidateInformation());
		}
	}
}