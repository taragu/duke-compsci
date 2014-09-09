package gameauthoring.workspace;

import java.util.List;

public interface InspectorLike {
	public void inspect(String type, String id);
	
	public void inspect(String type, List<String> id);
}
