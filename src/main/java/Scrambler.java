import java.util.HashMap;

/**
 * Created by master on 17.11.2016.
 */
public class Scrambler {
    String letters="0123456789+=- !.,";
    HashMap<String,String> encoder = new HashMap<String, String>();
    HashMap<String,String> decoder = new HashMap<String, String>();
    public Scrambler(){
        for (int i=0; i<letters.length();i++){
            encoder.put(letters.charAt(i)+"", String.format("%02d",i));
            decoder.put(String.format("%02d",i), letters.charAt(i)+"");
        }
        char ch='a';
        for (int i=letters.length(); i<26+letters.length(); i++,ch++){
            encoder.put(ch+"",String.format("%02d",i));
            decoder.put(String.format("%02d", i), ch+"");
        }
    }
    public String encode(String originalString){
        String result = "";
        for(int i=0; i<originalString.length();i++){
            if(encoder.containsKey(originalString.charAt(i)+"")){
                result+=encoder.get(originalString.charAt(i)+"");
            }
        }
        return result;
    }
    public String decode(String encodedString){
        String result="";
        String letter = "";
        for(int i=0; i<encodedString.length();i++){
            letter="";
            letter+=encodedString.charAt(i);
            i++;
            letter+=encodedString.charAt(i);
            if(decoder.containsKey(letter)){
                result+=decoder.get(letter);
            }else {
                System.err.println("Нет буквы "+letter);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scrambler scrambler= new Scrambler();
        System.out.println(scrambler.decode(scrambler.encode("hello world")));
    }
}
