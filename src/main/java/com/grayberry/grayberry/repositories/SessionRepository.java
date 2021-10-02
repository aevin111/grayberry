package com.grayberry.grayberry.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grayberry.grayberry.models.Session;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer>
{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO session VALUES (NULL, ?1, ?2)", nativeQuery = true)
    public void createSession(Integer userId, String sessionToken);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM session WHERE session_token = ?1", nativeQuery = true)
    public int destroySession(String sessionToken);
    @Query("SELECT s FROM session s WHERE sessionToken = ?1")
    public Session getSessionInfoFromToken(String sessionToken);
}
