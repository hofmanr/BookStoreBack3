package nl.rhofman.bookstore.ejb.message.dao;

import nl.rhofman.bookstore.ejb.message.domain.Metadata;
import nl.rhofman.bookstore.persist.model.MessageMetadata;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MetadataMapper {
    static MetadataMapper instance() {
        return Mappers.getMapper(MetadataMapper.class);
    }

    @Mapping(source = "messageID", target = "identification")
    @Mapping(source = "correlationID", target = "correlationId")
    @Mapping(source = "timestamp", target = "created")
    MessageMetadata mapToMessageMetadata(Metadata metadata);
}