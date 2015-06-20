package ngdc.geomag.web

import groovy.util.logging.Slf4j
import ngdc.geomag.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
//import org.springframework.web.servlet.ModelAndView
import org.springframework.beans.factory.annotation.Autowired
//import javax.sql.DataSource
//import org.springframework.jdbc.core.JdbcTemplate
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//import com.vividsolutions.jts.geom.Geometry
//import com.vividsolutions.jts.io.WKTReader
import org.springframework.http.HttpStatus
import groovy.json.*


// Use annotation to inject log field into the class.
@Slf4j
@RestController
@RequestMapping(value = "/crowdmag/measurements")
class MeasurementsController {
    def slurper = new JsonSlurper()

    @Autowired
    MeasurementRepository repository

/*
incoming payload in the format:
{
    "device":"string",
    "app_id":"string",
    "data":[{
        "obsTime": long,  //time since epoch,
        "latitude": double,
        "longitude": double,
        "altitude": int,
        "accuracy": int,
        "elem_X": double, // nanoTeslas, Strength in magnetic north   direction. Close to zero
        "elem_Y": double, // nanoTeslas, Horizontal Magnetic Field Strength or H
        "elem_Z": double  // nanoTeslas, Downward component of the magnetic field vector
    },...]
}
*/

    @RequestMapping(method = RequestMethod.POST)
    def createMeasurement(@RequestBody String jsonString) {
        def json = slurper.parseText(jsonString)
        def savedRecords = 0
        def measurement
        json.data.each {
            measurement = new Measurement(obsTime: new Date(it.obsTime),
                                          latitude: it.latitude,
                                          longitude: it.longitude,
                                          altitude: it.altitude,
                                          accuracy: it.accuracy,
                                          x: it.elem_X,
                                          y: it.elem_Y,
                                          z: it.elem_Z,
                                          device: json.device,
                                          appId: json.app_id)
            println measurement
            println measurement.validate()
            if (measurement.validate() && repository.save(measurement)) {
                savedRecords++
            }
        }

        def incomingRecordCount = json.data.size()
        println incomingRecordCount
        println "saved records: ${savedRecords}"
        return ["message":"success"]
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listMeasurements() {
        //TODO take start/end dates, valid, bbox as parameters
        log.info('returning data from the last 24 hours')
        println repository.findAll()
        return repository.findAll()
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(groovy.json.JsonException.class)
    @ResponseBody
    public String handleException(groovy.json.JsonException e) {
        log.warn "invalid JSON payload: ${e}"
        return new JsonBuilder(["error": "invalid JSON payload"]).toString()
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        //println "invalid JSON payload: ${e}"
        return new JsonBuilder(["error": "${e.message}"]).toString()
    }

}
