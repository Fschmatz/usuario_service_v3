package com.fschmatz.usuario_service_v3.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encrypt {

    public static String encrypt(String msg){
        StringBuffer sb = new StringBuffer(msg);
        String a = sb.reverse().toString();
        StringBuilder sc = new StringBuilder(a);

        String p =  sc.insert(1, "GñopDS1G 978F 7CS7F-{54").toString();

        p = sc.insert(0, "D(GS G9õmoga16*.98F {´´´798!?").toString();
        p = sc.insert(p.length() - 1, "´´sg  dg5d4àóîís g2dg9s7").toString();
        p = sc.insert(p.length(), "kl sco pesaox").toString();

        return p.toUpperCase();
    }

    public static String decrypt(String msg){
        StringBuilder sl = new StringBuilder(msg);

        sl.delete(0, 29);
        sl.delete(1, 24);
        sl.delete(sl.length() - 13, sl.length());
        sl.delete(sl.length() - 25, sl.length() - 1);
        StringBuffer sf = new StringBuffer(sl.toString());

        String x = sf.reverse().toString();

        return x;
    }
}