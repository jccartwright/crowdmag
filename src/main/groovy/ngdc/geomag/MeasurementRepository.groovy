package ngdc.geomag

import org.springframework.data.repository.CrudRepository;

interface MeasurementRepository extends CrudRepository<Measurement, Long> {
    List<Measurement> findByDevice(String device);
}
