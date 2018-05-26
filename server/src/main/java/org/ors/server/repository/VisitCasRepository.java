package org.ors.server.repository;

import org.ors.server.entity.VisitCas;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface VisitCasRepository extends CassandraRepository<VisitCas, String> {

}
