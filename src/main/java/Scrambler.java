import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import org.apache.log4j.lf5.util.StreamUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by master on 17.11.2016.
 */
public class Scrambler {
    String letters="0123456789+=- !.,";
    HashMap<String,String> encoder = new HashMap<String, String>();
    HashMap<String,String> decoder = new HashMap<String, String>();
    PrimeNumbers primeNumbers = new PrimeNumbers(1000);
    ArrayList<Integer> primes=primeNumbers.getPrimeNumbers(200,1000);
    BigInteger p,q,phi,e,d,n;
    public Scrambler(int e){
        Random random = new Random();
        for (int i=0; i<letters.length();i++){
            encoder.put(letters.charAt(i)+"", String.format("%02d",i));
            decoder.put(String.format("%02d",i), letters.charAt(i)+"");
        }
        char ch='a';
        for (int i=letters.length(); i<26+letters.length(); i++,ch++){
            encoder.put(ch+"",String.format("%02d",i));
            decoder.put(String.format("%02d", i), ch+"");
        }

        p= BigInteger.valueOf(primes.get(random.nextInt(50)));
        q= BigInteger.valueOf(primes.get(random.nextInt(50)));
        n=p.multiply(q);
        BigInteger minp, minq;
        minp=p.subtract(BigInteger.valueOf(1));
        minq=q.subtract(BigInteger.valueOf(1));
        phi=minp.multiply(minq);
        this.e= BigInteger.valueOf(e);
//        e=primes.get(random.nextInt(32));
        d=(this.e.modInverse(phi));

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
    public ArrayList<BigInteger> fullencode(String encodedString){
        ArrayList<BigInteger> result = new ArrayList<BigInteger>();
        String letter;
        for (int i=0; i<encodedString.length();i++){
            letter=encodedString.charAt(i)+"";
            i++;
            letter+=encodedString.charAt(i);
            BigInteger m= BigInteger.valueOf(Integer.parseInt(letter));
            BigInteger c1 = m.pow(e.intValue());
            BigInteger c = c1.mod(n);
//            System.out.print("c= "+c+"; m= "+m+"; e="+e+"; n="+n);
//            System.out.println();
            result.add(c);
        }
        return result;
    }
    public String fulldecode(ArrayList<BigInteger> fullyEncodedText){
        String result = "";
        for(BigInteger c:fullyEncodedText){
            BigInteger m1= c.pow(d.intValue());
            BigInteger m = m1.mod(n);
//            System.out.print("m= "+m+"; c= "+c+"; d="+d+"; n="+n);
//            System.out.println();
            result+=m;
        }
        return result;
    }


    public static void main(String[] args) {
       Logger logger = Logger.getLogger(Main.class);
        System.out.println("Please enter the public exponent");
        Scanner scanner = new Scanner(System.in);
        int e = scanner.nextInt();
        Scrambler scrambler = new Scrambler(e);
        logger.info("Секретная экспонента е: "+e);
        logger.info("Числа p и q. p= "+scrambler.p+"; q= "+scrambler.q);
        logger.info("Публичный ключ: "+scrambler.e+","+scrambler.n);
        logger.info("Приватный ключ: "+scrambler.d+","+scrambler.n);
        System.out.println("Введите текст для шифровки. Допустимы латинские буквы, цифры а также символы +=- !.,");
        scanner.nextLine();
        String originalString = scanner.nextLine();
        String firstEncode = scrambler.encode(originalString);
        logger.info("Введенный текст в числовом виде: "+originalString);
        String fullEncode = "";
        ArrayList<BigInteger> encoded = scrambler.fullencode(firstEncode);
        for(BigInteger value:encoded){
            fullEncode+=value;
        }
        logger.info("Текст, отправленный по сети: "+fullEncode);
        String firstDecode = scrambler.fulldecode(encoded);
        logger.info("Текст, после расшифровки закрытым ключом: "+firstDecode);
        String fullyDecoded = scrambler.decode(firstDecode);
        logger.info("Окончательно расшифрованный текст: "+fullyDecoded);

    }
}
