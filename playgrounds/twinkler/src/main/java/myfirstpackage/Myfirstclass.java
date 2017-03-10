package myfirstpackage;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

/**
 * Created by Thomas on 10.03.2017.
 */
public class Myfirstclass {

    public static void main(final String[] args) {
        Config config = ConfigUtils.loadConfig("C:/Users/Thomas/Documents/Git-MATsim/matsim/examples/scenarios/equil/config.xml");
        Scenario scenario = ScenarioUtils.loadScenario(config);
        Controler controler = new Controler(scenario);
        controler.run();
    }
}
