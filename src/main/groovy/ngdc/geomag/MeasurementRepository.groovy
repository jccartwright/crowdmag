package ngdc.geomag

import org.springframework.data.repository.CrudRepository
import com.vividsolutions.jts.geom.Geometry
import org.springframework.data.jpa.repository.Query

interface MeasurementRepository extends CrudRepository<Measurement, Long> {
    List<Measurement> findByDevice(String device);

    @Query("select m from Measurement m where within(m.geom, ?1) = true")
    List<Measurement> findWithin(Geometry filter)
}
