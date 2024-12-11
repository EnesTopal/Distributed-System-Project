package com.example.distributedProject.services;

import com.example.distributedProject.model.Event;
import com.example.distributedProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
