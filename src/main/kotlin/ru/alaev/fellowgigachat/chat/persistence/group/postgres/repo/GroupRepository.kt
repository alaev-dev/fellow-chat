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
}