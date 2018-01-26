package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataUtils {

    public static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static SimpleDateFormat formatoSemHorario = new SimpleDateFormat("dd/MM/yyyy");

    public static String deTimestamp(Timestamp time) {
        if(time == null){
            return "";
        }
        Calendar cld = Calendar.getInstance();
        cld.setTimeInMillis(time.getTime());
        return formato.format(cld.getTime());
    }

    public static String deTimestampSemHorario(Timestamp time) {
        if(time == null){
            return "";
        }
        Calendar cld = Calendar.getInstance();
        cld.setTimeInMillis(time.getTime());
        return formatoSemHorario.format(cld.getTime());
    }
    
    public static Timestamp paraTimestamp(String str) {
        Calendar cld = Calendar.getInstance();
        try {
            cld.setTime(formato.parse(str));
            return new Timestamp(cld.getTimeInMillis());
        } catch (ParseException ex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static Timestamp paraTimestampSemHorario(String str) {
        Calendar cld = Calendar.getInstance();
        try {
            cld.setTime(formatoSemHorario.parse(str));
            return new Timestamp(cld.getTimeInMillis());
        } catch (ParseException ex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
