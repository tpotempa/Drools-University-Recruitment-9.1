package pl.edu.atar.universityrecruitment;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniversityMainExpert {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniversityMainExpert.class);

    public static void main(String[] args) {

        // Tworzenie kolekcji faktów zawierającej wybranych kandydatów
        List<Candidate> uc = new ArrayList<Candidate>();
        uc.add(new Dataset().getUniversityCandidatesWithSubjects().get(0));

        KieServices kService = KieServices.Factory.get();
        KieContainer kContainer = kService.getKieClasspathContainer();
        KieBase kBase = kContainer.getKieBase("expert.nobligatory");

        for (Candidate candidate : uc) {

            KieSession kSession = kBase.newKieSession();

            LOGGER.info("Knowledge base created.\n\nREASONING AND ANALYSIS OF CANDIDATE ID = {}\n", candidate.getId());
            LOGGER.info("Information BEFORE reasoning.\n\n{}\n\n{}", candidate.getCandidateInformationLogger(), candidate.getCandidateSubjectsInformation());

            // Logowanie zebranych informacji
            kService.getLoggers().newConsoleLogger(kSession);
            kService.getLoggers().newFileLogger(kSession, "./logs/reasoning_expert_fact_" + candidate.getId());

            LOGGER.info("Number of facts in WORKING MEMORY (T1): {}", kSession.getFactCount());
            LOGGER.info("Adding fact(s) into WORKING MEMORY.");
            kSession.insert(candidate);
            LOGGER.info("Fact(s) added into WORKING MEMORY.");
            LOGGER.info("Number of facts in WORKING MEMORY (T2): {}\n", kSession.getFactCount());

            // Wskazanie do przetwarzania zbiorów reguł kwalifikacyjnych "exam_result_calculation"
            kSession.getAgenda().getAgendaGroup("exam_result_calculation").setFocus();

            LOGGER.info("... REASONING STARTED ...");
            kSession.fireAllRules();
            LOGGER.info("... REASONING ENDED ...\n");

            LOGGER.info("Number of facts in WORKING MEMORY (T3): {}\n", kSession.getFactCount());
            LOGGER.info("Information AFTER reasoning.\n\n{}{}", candidate.getCandidateInformationLogger(), candidate.getReasoningLogger());

            // Zwolnienie pamięci
            kSession.dispose();
            LOGGER.info("Number of facts in WORKING MEMORY (T4): {}\n", kSession.getFactCount());
        }
    }
}