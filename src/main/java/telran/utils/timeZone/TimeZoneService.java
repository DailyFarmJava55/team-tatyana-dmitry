//package telran.utils.timeZone;
//
//import org.springframework.stereotype.Service;
//import org.springframework.stereotype.Service;
//
//import java.awt.geom.Point2D;
//import java.time.ZoneId;
//import java.util.TimeZone;
//
//@Service
//public class TimeZoneService {
//	public String getTimeZoneByLocation(double latitude, double longitude) {
//        GeodeticCalculator calculator = new GeodeticCalculator();
//        calculator.setStartingGeographicPoint(longitude, latitude);
//
//        TimeZone timeZone = TimeZone.getTimeZone(ZoneId.ofOffset("GMT", calculator.getGeodeticPosition().getOffset()));
//        
//        if (timeZone == null || timeZone.getID().equals("GMT")) {
//            return "GMT";
//        }
//
//        return timeZone.getID();
//    }
//}
