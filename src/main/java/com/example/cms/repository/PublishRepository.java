package com.example.cms.repository;

import java.lang.foreign.Linker.Option;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.Publish;

public interface PublishRepository extends JpaRepository<Publish, Integer> {

}
