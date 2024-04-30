package com.exam.surveytool.repositories;

import com.exam.surveytool.models.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {
    // Här kan du lägga till ytterligare query-metoder om det behövs.
}
