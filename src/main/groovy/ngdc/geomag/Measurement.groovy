package ngdc.geomag

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.vividsolutions.jts.geom.Geometry
import org.hibernate.annotations.Type
import groovy.transform.ToString

@Entity
@ToString(includeNames=true, includeFields = true)
class Measurement {
    @Id
    @GeneratedValue
    private long id
    Date obsTime
    Double longitude    //-180 <= longitude <= 180
    Double latitude     //-90 <= latitude <= 90
    Double altitude
    Double accuracy
    Double x            // nanoTeslas, Strength in magnetic north   direction. Close to zero
    Double y            // nanoTeslas, Horizontal Magnetic Field Strength or H
    Double z            // nanoTeslas, Downward component of the magnetic field vector
    String device
    Integer appId

    @Type(type="org.hibernate.spatial.GeometryType")
    private Geometry geom


    //TODO consider using validation framework like JSR-303
    Boolean validate() {
        if (obsTime == null ||
            longitude == null ||
            latitude == null ||
            altitude == null ||
            accuracy == null ||
            x == null ||
            y == null ||
            z == null ||
            device == null) {
            println "missing required field"
            return false
        }

        //obsTime <= now
        if (! obsTime instanceof Date) {
            println "obsTime must be Date"
            return false
        }

        def now = new Date()
        //def obsTime = record.obsTime.toLong()
        if (obsTime.time >= now.time ) {
            println "obsTime must be older current time"
            return false
        }

        //-180 <= longitude <= 180
        if (! longitude instanceof Double) {
            println "longitude must be Double"
            return false
        }
        if (longitude < -180.0 || longitude > 180.0) {
            println "longitude must be >= -180 and <= 180"
            return false
        }

        //-90 <= latitude <= 90
        if (! latitude instanceof Double) {
            println "latitude must be Double"
            return false
        }
        if (latitude < -90.0 || latitude > 90.0) {
            println "latitude must be >= -90 and <= 90"
            return false
        }

        //allow [a-zA-Z_0-9 -,]
        println "device: ${device}, match: ${device ==~ /[\w \\\-,]*/}"
        if (! (device ==~ /[\w \\\-,]*/)) {
            println "device name contains invalid characters"
            return false
        }

        //only check datatype of remaining values
        if (! altitude instanceof Integer) {
            println "altitude must be Integer"
            return false
        }
        if (! accuracy instanceof Integer) {
            println "accuracy must be Integer"
            return false
        }
        if (! x instanceof Double) {
            println "x must be Double"
            return false
        }
        if (! y instanceof Double) {
            println "y must be Double"
            return false
        }
        if (! z instanceof Double) {
            println "z must be Double"
            return false
        }

        return true
    }
}
