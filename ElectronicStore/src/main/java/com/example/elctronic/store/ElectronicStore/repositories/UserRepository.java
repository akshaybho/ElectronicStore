package com.example.elctronic.store.ElectronicStore.repositories;

import com.example.elctronic.store.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional <User> findByEmailAndPassword(String email, String password);

    List<User> findByNameContaining(String keywords);
}
