package univpm.oopproject;

import java.util.ArrayList;
import java.util.List;

public class Dataset {

	private static List<Person> data = new ArrayList<Person>();
	
	public static void addPerson(Person p) {
		data.add(p);
	}
}
