package ru.polskiy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.polskiy.dto.WorkspaceDto;
import ru.polskiy.model.entity.Workspace;

@Mapper
public interface WorkspaceMapper {

    WorkspaceDto toDto(Workspace meterReading);

    @Mapping(target = "userId", ignore = true)
    Workspace toEntity(WorkspaceDto dto);
}
