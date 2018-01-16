package util;

import java.math.BigDecimal;

public class NumUtils {
    
    public static String formataValor(BigDecimal valor){
        if (valor == null){
            return "R$ 0,00";
        }
        BigDecimal valorArredondado = valor.setScale(2, BigDecimal.ROUND_HALF_UP);
        return "R$ " + valorArredondado.toPlainString().replace('.', ',');
    }
    
    public static BigDecimal desformataValor(String str){
        if(str == null){
            return BigDecimal.ZERO;
        }
        str = str.replace(',', '.').replace("R$ ", "");
        return new BigDecimal(str);
    }
    
}
