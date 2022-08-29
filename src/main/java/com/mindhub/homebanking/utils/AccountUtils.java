package com.mindhub.homebanking.utils;
import org.springframework.util.ResourceUtils;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountUtils {
    public AccountUtils() {
    }
    public static String generateNewAlias() throws IOException {
        return (getRandomWord() + "." + getRandomWord() + "." + getRandomWord());
    }
    private static String getRandomWord() throws IOException {
        // cargamos el archivo static/words.txt en la variable words
        File words = ResourceUtils.getFile("classpath:static/words.txt");
        // Leemos el archivo linea a linea y creamos una lista de palabras list
        BufferedReader in = new BufferedReader(new FileReader(words));
        String str;

        List<String> list = new ArrayList<String>();
        while((str = in.readLine()) != null){
            list.add(str);
        }
        // Mezclamos la lista de palabras (random) y devolvemos la primer palabra de la lista
        Collections.shuffle(list);
        return list.get(0);
    }
    public static String generateNewCBU(String number) {
        return ("0017" + (int) (Math.random() * (100)) + (int) (Math.random() * (100000000))+ number.substring(4)+ (int) (Math.random() * (10)));
    }
    public static String generateAccountNumber(){
        return ("VIN-"+(int) ((Math.random() * (10000000 - 1000000)) + 1000000));
    }


}
