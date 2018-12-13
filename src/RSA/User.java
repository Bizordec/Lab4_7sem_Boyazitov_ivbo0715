package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;

public class User {
    private BigInteger[] publicKey = new BigInteger[2];
    private BigInteger[] secretKey = new BigInteger[2];

    public User() {
        BigInteger p, q, n, euler_val, e, d;
        BigInteger one = BigInteger.valueOf(1);

        // p and q are random prime numbers with the size of bitLength
        int bitLength = 1024;
        SecureRandom rnd = new SecureRandom();
        p = BigInteger.probablePrime(bitLength, rnd);
        q = BigInteger.probablePrime(bitLength, rnd);

        // n = p*q
        n = p.multiply(q);

        // Ф(n) = (p - 1)*(q - 1)
        euler_val = p.subtract(one).multiply(q.subtract(one));

        // 1 < e < Ф(n),  gcd(e, Ф) = 1
        e = BigInteger.valueOf(2);
        while ((e.compareTo(euler_val) < 0) && !euler_val.gcd(e).equals(one)) {
            e = e.add(one);
        }

        // 1 < d < Ф, e*d = 1(mod Ф(n))
        // or, in other words,
        // d = e^(-1) mod Ф(n)
        d = e.modInverse(euler_val);

        publicKey[0] = e;
        publicKey[1] = n;

        secretKey[0] = d;
        secretKey[1] = n;
    }

    public BigInteger[] getPublicKey() {
        return publicKey;
    }

    public BigInteger[] encrypt(BigInteger[] publicKey, String message) {
        char[] m_chars = message.toCharArray();
        BigInteger[] encrypted_blocks = new BigInteger[m_chars.length];
        for (int i = 0; i < m_chars.length; i++) {
            // c = m^e mod n
            encrypted_blocks[i] = (BigInteger.valueOf((int) m_chars[i])).modPow(publicKey[0], publicKey[1]);
        }
        return encrypted_blocks;
    }

    public String decrypt(BigInteger[] message) {
        System.out.println("Secret key [d, n] is: " + Arrays.toString(secretKey));

        char[] m_chars = new char[message.length];
        StringBuilder decrypted_str = new StringBuilder();
        for (int i = 0; i < message.length; i++) {
            // m = c^d mod n
            m_chars[i] = (char) message[i].modPow(secretKey[0], secretKey[1]).intValue();
            decrypted_str.append(m_chars[i]);
        }
        return decrypted_str.toString();
    }
}
