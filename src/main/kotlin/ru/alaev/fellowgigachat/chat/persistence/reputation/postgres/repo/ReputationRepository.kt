package ru.alaev.fellowgigachat.chat.persistence.reputation.postgres.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.alaev.fellowgigachat.chat.persistence.reputation.postgres.model.ReputationEntity

@Repository
interface ReputationRepository : CrudRepository<ReputationEntity, Long> {

    @Query("SELECT r FROM ReputationEntity r JOIN r.user u WHERE u.username = :username")
    fun findByUsername(@Param("username") username: String): ReputationEntity?

}
