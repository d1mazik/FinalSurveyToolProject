package com.exam.surveytool.repositories;

import com.exam.surveytool.models.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
