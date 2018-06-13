package org.ors.server.repository;

import org.ors.server.entity.RelocationCas;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface RelocationCasRepository extends CassandraRepository<RelocationCas, String> {
}
