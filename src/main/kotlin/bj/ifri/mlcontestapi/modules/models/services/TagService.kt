package bj.ifri.mlcontestapi.modules.models.services

import bj.ifri.mlcontestapi.modules.models.dto.TagDto
import bj.ifri.mlcontestapi.modules.models.dto.TagFilterDto
import bj.ifri.mlcontestapi.modules.models.entities.Tag
import bj.ifri.mlcontestapi.modules.models.repositories.TagRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class TagService(private val tagRepository: TagRepository) {
    fun list(tagFilterDto: TagFilterDto): Page<TagDto> {
        val pageable = PageRequest.of(tagFilterDto.page, tagFilterDto.perPage)
        val tags = if (!tagFilterDto.search.isNullOrBlank()) {
            tagRepository.findByName(tagFilterDto.search!!, pageable)
        } else {
            tagRepository.findAll(pageable)
        }

        return tags.map(TagDto::fromTag)
    }

    fun getById(id: Long): TagDto? {
        return tagRepository.findById(id)
            .orElse(null)
            ?.let(TagDto::fromTag)
    }

    fun createInBatch(tags: List<String>): List<Tag> {
        val found = tagRepository.findByNameIn(tags)
        val namesFound = found.map { it.name }
        val namesToStore = tags.filter { !namesFound.contains(it) }
        val tagsToStore = namesToStore.map { Tag(name = it) }

        val stored = tagRepository.saveAll(tagsToStore)
        return stored + found
    }
}