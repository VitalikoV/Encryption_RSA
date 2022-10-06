import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    private BigInteger p, q;
    private BigInteger n;
    private BigInteger PhiN;
    private BigInteger e, d;

    public Main() {
        initialize();
    }

    public void initialize() {
        int SIZE = 512;
        /* B-8:  p = 31, q = 37. */
        p = BigInteger.valueOf(31);
        q = BigInteger.valueOf(37);

        /*  n = p.q */
        n = p.multiply(q);
        /*Функція Ейлера: ø(n) = (p - 1).(q - 1) */
        PhiN = p.subtract(BigInteger.valueOf(1));
        PhiN = PhiN.multiply(q.subtract(BigInteger.valueOf(1)));

        /*  e: gcd(e, ø(n)) = 1 ; 1 < e < ø(n) */
        do {
            e = new BigInteger(2 * SIZE, new Random());
        } while ((e.compareTo(PhiN) != 1)
                || (e.gcd(PhiN).compareTo(BigInteger.valueOf(1)) != 0));

        /* d: e.d = 1 (mod ø(n)) */
        d = e.modInverse(PhiN);
    }

    public BigInteger encrypt(BigInteger plaintext) {
        return plaintext.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger ciphertext) {
        return ciphertext.modPow(d, n);
    }

    public static void main(String[] args) throws IOException {
        Main app = new Main();
        String plaintext = "РАНГ01";
        BigInteger bplaintext, bciphertext;
        char[] letters = plaintext.toCharArray();
        List<Integer> letterCodes = new ArrayList<>();
        for(char letter : letters){
            letterCodes.add((int) letter);
        }
        List<BigInteger> encryptedLetterCodes = new ArrayList<>();

        for(int code : letterCodes){
            encryptedLetterCodes.add(app.encrypt(BigInteger.valueOf(code)));
        }

        System.out.println("Текст : " + letterCodes);
        System.out.println("Зашифрований текст : " + encryptedLetterCodes);

        List<BigInteger> decryptedLetterCodes = new ArrayList<>();
        for(BigInteger code : encryptedLetterCodes) {
            decryptedLetterCodes.add(app.decrypt(code));
        }

        System.out.println("Після розшифрування : " + decryptedLetterCodes);


        System.out.print("Друге завдання: ");

        //B-8
        List<Integer> data = Arrays.asList(547, 894, 725, 247, 1081, 803, 219, 205);

        for (Integer num : data) {
            BigInteger dec = app.decrypt(BigInteger.valueOf(num));
            System.out.print((char) Integer.parseInt(dec.toString()));
        }

    }

}