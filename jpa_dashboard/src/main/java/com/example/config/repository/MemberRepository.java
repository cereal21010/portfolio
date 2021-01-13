package com.example.config.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	Optional<Member> findById(String id);
}
