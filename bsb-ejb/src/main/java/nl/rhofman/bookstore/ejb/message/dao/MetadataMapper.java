package nl.rhofman.bookstore.ejb.message.dao;

import nl.rhofman.bookstore.ejb.message.domain.Header;
import nl.rhofman.bookstore.persist.model.Metadata;
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
    @Mapping(source = "sender", target = "messageSender")
    @Mapping(source = "recipient", target = "messageRecipient")
    Metadata mapToMessageMetadata(Header header);
}