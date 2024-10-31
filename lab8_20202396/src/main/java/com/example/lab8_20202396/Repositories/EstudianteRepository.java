package com.example.lab8_20202396.Repositories;

import com.example.lab8_20202396.Entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query("SELECT e FROM Estudiante e WHERE (:facultad IS NULL OR e.facultad = :facultad) ORDER BY e.gpa DESC")
    List<Estudiante> findByFacultadOrderByGpaDesc(@Param("facultad") String facultad);


}
