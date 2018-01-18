package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataUtils {

    public static SimpleDateFormat formato = new SimpleDateFormat("dd/MM/aaaa HH:mm:ss");

    public static String formataData(Timestamp time) {
        Calendar cld = Calendar.getInstance();
        cld.setTimeInMillis(time.getTime());
        return formato.format(cld.getTime());
    }

    public static Timestamp desformataData(String str) {
        Calendar cld = Calendar.getInstance();
        try {
            cld.setTime(formato.parse(str));
        } catch (ParseException ex) {
            Logger.getLogger(DataUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Timestamp(cld.getTimeInMillis());
    }

}
