package com.thoughtworks.sample.version.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VersionRepository extends JpaRepository<Version,Integer> {

    @Query(value = "SELECT NAME FROM VERSION ORDER BY ID DESC",nativeQuery = true)
    List<String> getLatestVersion();
}
