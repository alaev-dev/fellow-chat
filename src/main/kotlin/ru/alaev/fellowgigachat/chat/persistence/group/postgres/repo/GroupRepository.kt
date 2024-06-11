package ru.alaev.fellowgigachat.chat.persistence.group.postgres.repo

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.alaev.fellowgigachat.chat.persistence.group.postgres.model.GroupEntity
import ru.alaev.fellowgigachat.chat.persistence.user.postgres.model.UserEntity

@Repository
interface GroupRepository : CrudRepository<GroupEntity, Long> {

    @Query("select g from GroupEntity g where upper(g.name) = upper(:name)")
    fun findByName(@Param("name") name: String): GroupEntity?


    @Query("select g.users from GroupEntity g where g.name = :name")
    fun findUsersByGroup(@Param("name") name: String): List<UserEntity>

    @Query(
        """
        select g from GroupEntity g
        join g.users u
        where u.username in :usernames
        group by g.id
        having count(u) = :size
        and count(u) = (
            select count(u2) from GroupEntity g2
            join g2.users u2
            where g2.id = g.id
        )
    """
    )
    fun findByExactUsernames(@Param("usernames") usernames: Set<String>, @Param("size") size: Long): GroupEntity?
}