package utils;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.egartech.sppi.model.*;

@Service
public class ProcessUtils {

    @Autowired
    private KieContainer kieContainer;
    
    public boolean checkColor(com.egartech.sppi.model.Process process) {
    	StatelessKieSession kieSession = kieContainer.newStatelessKieSession();
        kieSession.execute(process);
    	return false;
    }
	
}