package ru.polskiy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.dto.WorkspaceDto;
import ru.polskiy.model.entity.Workspace;

/**
 * Mapper interface for converting between {@link Workspace} entities and {@link WorkspaceDto} DTOs.
 * This interface uses MapStruct to generate the implementation automatically at build time.
 */
@Mapper
public interface WorkspaceMapper {

    /**
     * Converts a {@link Workspace} entity to a {@link WorkspaceDto}.
     *
     * @param entity the {@link Workspace} entity to convert
     * @return the converted {@link WorkspaceDto}
     */
    WorkspaceDto toDto(Workspace entity);

    /**
     * Converts a {@link WorkspaceDto} to a {@link Workspace} entity.
     *
     * @param dto the {@link WorkspaceDto} to convert
     * @return the converted {@link Workspace} entity
     */
    @Mapping(target = "userId", ignore = true)
    Workspace toEntity(WorkspaceDto dto);
}