package gameauthoring.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayMap<E,F> {
	private List<E> myKeys = new ArrayList<E>();
	private List<F> myValues = new ArrayList<F>();

	public List<E> getKeys(){
		return myKeys;
	}
	
	public List<F> getValues(){
		return myValues;
	}
	
	public ArrayMap<E, F> clone(){
		ArrayMap<E,F> toReturn = new ArrayMap<E,F>();
		
		toReturn.myKeys = new ArrayList<E>(this.myKeys);
		toReturn.myValues = new ArrayList<F>(this.myValues);
		
		return toReturn;
	}
	
	public void combine(ArrayMap<E, F> toAdd){
		for (Integer count=0; count<toAdd.size(); count++){
			E key = toAdd.getKeys().get(count);
			F value = toAdd.getValues().get(count);
			this.put(key, value);
		}
	}
	
	public void put(E key, F value){
		
		myKeys.add(key);
		myValues.add(value);
		
	}
	
	public Integer size(){
		return myKeys.size();
	}
	
	public Set<E> getUniqueKeys(){
		Set<E> toReturn = new HashSet<E>();
		
		for (E e: myKeys)
			toReturn.add(e);
		
		return toReturn;
	}
	
	public List<F> getValueList(E key){
		List<F> toReturn = new ArrayList<F>();
		List<Integer> indexList = getIndexList(key, myKeys);
		for (Integer count: indexList){
			toReturn.add(myValues.get(count));
		}
		
		return toReturn;
	}
	
	public void compress(){
		ArrayMap<E,F> arrayMap = new ArrayMap<E,F>();
		for (E key: this.getUniqueKeys()){
        		arrayMap.put(key, this.getValueList(key).get(0));
        }
		myKeys = arrayMap.getKeys();
		myValues = arrayMap.getValues();
        
	}
	
	private List<Integer> getIndexList(Object key, List<?> myList){
		List<Integer> toReturn = new ArrayList<Integer>();
		
		for (Integer count=0; count<myList.size(); count++){
			Object current = myList.get(count);
			if (current.equals(key)){
				toReturn.add(count);
			}
		}	
		return toReturn;
	}
	
}
