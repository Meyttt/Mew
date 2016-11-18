import java.util.ArrayList;

/**
 * Created by Admin on 15.11.2016.
 */
public class PrimeNumbers {
	private ArrayList<Boolean> list = new ArrayList<Boolean>();
	public PrimeNumbers(int size){
		for(int i =0;i<size;i++){
			list.add(i,true);
		}
	}
	public ArrayList<Integer> getPrimeNumbers(){
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i=2; i<Math.sqrt(list.size());i++){
			if(list.get(i)) {
				result.add(i);
				for (int j = i; j < list.size(); j++) {
					if (list.get(j) && ((j % i) == 0)) {
						this.list.set(j, false);
					}
				}
			}
		}
		for(int i=2;i<list.size();i++){
			if(list.get(i)){
				result.add(i);
			}
		}
		return result;
	}
	public ArrayList<Integer> getPrimeNumbers(int min, int max){
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i=2; i<Math.sqrt(list.size());i++){
			if(list.get(i)) {
				result.add(i);
				for (int j = i; j < list.size(); j++) {
					if (list.get(j) && ((j % i) == 0)) {
						this.list.set(j, false);
					}
				}
			}
		}
		int localMax= list.size()<max ? list.size() : max;
		for(int i=min;i<localMax;i++){
			if(list.get(i)){
				result.add(i);
			}
		}
		return  result;
	}
	public static void showResult(ArrayList<Integer> result){
		for(Integer value:result){
			System.out.println(value);
		}
	}

	public static void main(String[] args) {
		PrimeNumbers primeNumbers=new PrimeNumbers(100000);
		ArrayList<Integer> result = primeNumbers.getPrimeNumbers();
		System.out.println(result.size());
		showResult(result);
	}
}
