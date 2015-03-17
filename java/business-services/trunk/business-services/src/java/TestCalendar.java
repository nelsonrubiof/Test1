
import hidden.org.codehaus.plexus.interpolation.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Calendar;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Admin
 */
public class TestCalendar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //SUN = 1 SAT = 7 
        Integer initDayOfWeek = 4;
        Integer endDayOfWeek = 3;
        Calendar cal = Calendar.getInstance();
        Integer dayActual = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println("dayActual: " +dayActual);
        // 4,5,6,7,1,2,3
        Integer[] arrayDays;
        Integer actual = null;
        arrayDays = new Integer[7];
        for (int i = 0; i < 7; i++) {
            if (actual == null) {
                actual = initDayOfWeek;
            } else {
                actual = actual + 1;
            }
            if (actual > 7) {
                actual = 1;
            }
            arrayDays[i] = actual;
        }

        System.out.println(Arrays.toString(arrayDays));
        int count = 0;
        for (Integer day : arrayDays) {
            if (day == dayActual) {
                break;
            } else {
                count = count + 1;
            }
        }
        System.out.println(count);

    }

}
