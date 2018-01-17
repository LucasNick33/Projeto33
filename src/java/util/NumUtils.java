package util;

import java.math.BigDecimal;

public class NumUtils {
    
    public static String formataValorMonetario(BigDecimal valor){
        if (valor == null){
            return "R$ 0,00";
        }
        BigDecimal valorArredondado = arredondar(valor, 2);
        return "R$ " + valorArredondado.toPlainString().replace('.', ',');
    }
    
    public static String formataValor(BigDecimal valor){
        if (valor == null){
            return "0,00";
        }
        BigDecimal valorArredondado = arredondar(valor, 2);
        return valorArredondado.toPlainString().replace('.', ',');
    }
    
    public static BigDecimal desformataValor(String str){
        if(str == null){
            return BigDecimal.ZERO;
        }
        str = str.replace(',', '.').replace("R$ ", "");
        return new BigDecimal(str);
    }
    
    public static BigDecimal arredondar(BigDecimal valor, int casasDecimais){
        return valor.setScale(casasDecimais, BigDecimal.ROUND_HALF_UP);
    }
    
    public static String quantidade(BigDecimal valor){
        if(valor.compareTo(new BigDecimal(valor.intValue())) == 0){
            return arredondar(valor, 0).toString();
        }
        return arredondar(valor, 3).toString().replace('.', ',');
    }
    
}
