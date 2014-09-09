package nodes.mathnodes;

import java.util.ArrayList;
import java.util.List;

public class MathResults {

	/**
	 * Because static, need to clear list after appending to GUI
	 */
	
	static List<String> mathResults = new ArrayList<String>();
	
	public static List<String> getMathResultsList(){
		return mathResults;
	}
	
	public static void addToMathResultsList(String result){
		mathResults.add(result);
	}

}
