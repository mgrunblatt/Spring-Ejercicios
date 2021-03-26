package com.example.demo.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MorseService {

    private String codigoMorse;
    private String codigoDecrifrado;


    public static String convertidorMorse(String codigo){
        HashMap<String, String> equivaleciesMap=obtenerEquivalencias();
        String codigoDecodificado="";


        String cadenaAux[]=codigo.replace("   "," x ").split(" ");

        for(int i=0;i<cadenaAux.length;i++){
            if(cadenaAux[i].equals("x")){
                codigoDecodificado+=" ";
            }else {
                codigoDecodificado += getKey(equivaleciesMap, cadenaAux[i]);
            }
        }
        return codigoDecodificado;
    }

    public static <K, V> K getKey(HashMap<K, V> map, V value) {
        for (HashMap.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }


    public static HashMap<String, String> obtenerEquivalencias() {
        HashMap<String, String> equivalencias = new HashMap<>();
        equivalencias.put("A", ".-");
        equivalencias.put("B", "-...");
        equivalencias.put("C", "-.-.");
        equivalencias.put("CH", "----");
        equivalencias.put("D", "-..");
        equivalencias.put("E", ".");
        equivalencias.put("F", "..-.");
        equivalencias.put("G", "--.");
        equivalencias.put("H", "....");
        equivalencias.put("I", "..");
        equivalencias.put("J", ".---");
        equivalencias.put("K", "-.-");
        equivalencias.put("L", ".-..");
        equivalencias.put("M", "--");
        equivalencias.put("N", "-.");
        equivalencias.put("Ñ", "--.--");
        equivalencias.put("O", "---");
        equivalencias.put("P", ".--.");
        equivalencias.put("Q", "--.-");
        equivalencias.put("R", ".-.");
        equivalencias.put("S", "...");
        equivalencias.put("T", "-");
        equivalencias.put("U", "..-");
        equivalencias.put("V", "...-");
        equivalencias.put("W", ".--");
        equivalencias.put("X", "-..-");
        equivalencias.put("Y", "-.--");
        equivalencias.put("Z", "--..");
        equivalencias.put("0", "-----");
        equivalencias.put("1", ".----");
        equivalencias.put("2", "..---");
        equivalencias.put("3", "...--");
        equivalencias.put("4", "....-");
        equivalencias.put("5", ".....");
        equivalencias.put("6", "-....");
        equivalencias.put("7", "--...");
        equivalencias.put("8", "---..");
        equivalencias.put("9", "----.");
        equivalencias.put(".", ".-.-.-");
        equivalencias.put(",", "--..--");
        equivalencias.put(":", "---...");
        equivalencias.put("?", "..--..");
        equivalencias.put("'", ".----.");
        equivalencias.put("-", "-....-");
        equivalencias.put("/", "-..-.");
        equivalencias.put("\"", ".-..-.");
        equivalencias.put("@", ".--.-.");
        equivalencias.put("=", "-...-");
        equivalencias.put("!", "−.−.−−");
        return equivalencias;
    }

}
