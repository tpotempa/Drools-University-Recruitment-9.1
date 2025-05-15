package pl.edu.atar.universityrecruitment;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.logger.KieRuntimeLogger;

public class UniversityMainMasterful {

	public static void main(String[] args) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-rules-masterful");
		
        // Tworzenie kolekcji faktów
        List<Candidate> candidates = new Dataset().getUniversityCandidates();

		// Przetwarzanie powinno być realizowane TYLKO dla pojedynczego faktu
		// Dodawany fakt należy podać jako parametr metody get.
		Candidate candidate = candidates.get(1);
		kSession.insert(candidate);

		// ZESTAW REGUŁ ZAAWANSOWANYCH

		// OPIS: Uruchamianie 2 zbiorów reguł kwalifikacyjnych. Zbiory reguł nie
		// wykluczają się tj. mogą być uruchomione reguły z obu zbiorów.
		// UWAGA: Dla prawidłowej analizy działania własności PropertyReactive
		// należy zmodyfikować klasę UniversityCandidate dodając wiersz kodu:
		// @org.kie.api.definition.type.PropertyReactive
		// PYTANIE: W jakiej kolejności wykonują się reguły?
		// Rezultaty działania silnika wnioskującego są zwracane w oknie konsoli.
		
		kSession.getAgenda().getAgendaGroup("two_sets_of_rules_salience_activation-group_MODIFY_PropertyReactive").setFocus();
		
		System.out.println("Number of facts in Working Memory (Entry Point): " + kSession.getFactCount());
		kSession.addEventListener(new DebugAgendaEventListener());
		kSession.addEventListener(new DebugRuleRuntimeEventListener());
		KieRuntimeLogger logger = ks.getLoggers().newFileLogger(kSession, "./rules-logger");
		
		kSession.fireAllRules();
		kSession.dispose();
				
		// Logowanie zebranych informacji
		System.out.println(candidate.getCandidateInformation());
	}
}