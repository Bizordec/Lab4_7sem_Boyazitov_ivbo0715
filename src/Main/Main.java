package Main;

import RSA.User;

import java.math.BigInteger;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        User alice = new User();
        User bob = new User();

        System.out.println("Bob encrypts the message");
        String message = "Hello there";
        System.out.println("Bob got Alice's public key [e, n]: " + Arrays.toString(alice.getPublicKey()));
        System.out.println("Source message is: " + message);
        BigInteger[] crypted_bigInt = bob.encrypt(alice.getPublicKey(), message);
        StringBuilder sb = new StringBuilder();
        for (BigInteger value : crypted_bigInt) {
            sb.append(value);
        }
        String crypted_string = sb.toString();
        System.out.println("Encrypted message is: " + crypted_string);

        System.out.println();

        System.out.println("Alice decrypts the message");
        String decrypted = alice.decrypt(crypted_bigInt);
        System.out.println("Decrypted message is: " + decrypted);
    }
}
