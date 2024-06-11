package ru.alaev.fellowgigachat.chat.persistence.group.postgres

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.alaev.fellowgigachat.chat.persistence.group.GroupStorage
import ru.alaev.fellowgigachat.chat.persistence.group.postgres.model.GroupEntity
import ru.alaev.fellowgigachat.chat.persistence.group.postgres.repo.GroupRepository
import ru.alaev.fellowgigachat.chat.persistence.user.UserStorage
import ru.alaev.fellowgigachat.domain.GroupId
import ru.alaev.fellowgigachat.domain.GroupName
import ru.alaev.fellowgigachat.domain.Username

@Service
class PostgresGroupStorage(
    private val groupRepository: GroupRepository,
    private val userStorage: UserStorage
) : GroupStorage {

    private val logger = LoggerFactory.getLogger(PostgresGroupStorage::class.java)

    @Transactional
    override fun getOrCreate(groupName: GroupName, users: List<Username>): GroupEntity {
        logger.info("getOrCreate called with groupName: ${groupName.value} and users: ${users.map { it.value }}")

        val group = groupRepository.findByName(groupName.value)
        if (group != null) {
            logger.info("Group found: ${group.name}")
            return group
        }

        logger.info("Group not found, creating a new one")
        val userEntities = users.map { userStorage.getUser(it) ?: userStorage.createEmptyUser(it) }.toMutableSet()

        val newGroup = groupRepository.save(
            GroupEntity(
                name = groupName.value,
                users = userEntities
            )
        )

        val savedGroup = groupRepository.save(newGroup)

        logger.info("New group saved: ${savedGroup.name} with users: ${savedGroup.users.map { it.username }}")

        return savedGroup
    }

    override fun get(groupName: GroupName): GroupEntity? {
        logger.info("get called with groupName: ${groupName.value}")
        return groupRepository.findByName(groupName.value)
    }

    override fun getMembers(groupName: GroupName): List<Username> {
        logger.info("getMembers called with groupName: ${groupName.value}")
        return groupRepository.findUsersByGroup(groupName.value).map { Username(it.username) }
    }

    override fun createGroupForMembers(usernames: List<Username>): GroupEntity {
        getGroupByMembers(usernames)?.let { return it }

        return groupRepository.save(
            GroupEntity(
                id = 0,
                name = "PERSONAL-${usernames.sortedBy { it.value }.joinToString("-") { it.value }}",
                users = usernames.map { userStorage.getUser(it) ?: userStorage.createEmptyUser(it) }.toMutableSet()
            )
        )
    }

    override fun getGroupByMembers(usernames: List<Username>): GroupEntity? {
        if (usernames.isEmpty()) return null
        return groupRepository.findByExactUsernames(usernames.map { it.value }.toSet(), usernames.toSet().size.toLong())
    }

    override fun getById(id: GroupId): GroupEntity? {
        return groupRepository.findById(id.value).orElse(null)
    }
}