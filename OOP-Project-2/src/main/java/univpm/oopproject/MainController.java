package univpm.oopproject;

import org.springframework.web.bind.annotation.RestController;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class MainController {
    
    @RequestMapping("/")
    public String index()
    {
    	return "Dataset size: " + Dataset.getDataset().size() + " people.";
    }
    	
    @RequestMapping("/full")
    public JSONObject getFullData()
    {
    	return Dataset.getJSONDataset();
    }
    
}
