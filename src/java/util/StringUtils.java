package util;

public class StringUtils {
    
    public static String retirarAcentos(String str){
        if(str == null){
            return "";
        }
        String comAcentos = "ÁÀÃÂÄÉÈÊËÍÌÎÏÓÒÕÔÖÚÙÛÜÝÇ";
        String semAcentos = "AAAAAEEEEIIIIOOOOOUUUUYC";
        comAcentos += comAcentos.toLowerCase();
        semAcentos += semAcentos.toLowerCase();
        for (int i = 0; i < comAcentos.length(); i++) {
            str = str.replace(comAcentos.charAt(i), semAcentos.charAt(i));
        }
        return str;
    }
    
    public static String padronizar(String str){
        return retirarAcentos(str).trim().toUpperCase();
    }
    
}
