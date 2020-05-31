package de.stellaris.infrastructure;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.stellaris.domain.technology.ReaderException;
import de.stellaris.domain.technology.Technology;
import de.stellaris.domain.technology.TechnologyReader;

public class TechnologyReaderJson implements TechnologyReader {

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public List<Technology> readTechnologies(Path file) throws ReaderException {

    try {
      return mapper.readValue(file.toFile(), new TypeReference<List<Technology>>() {
      });
    } catch (IOException e) {
      throw new ReaderException(String.format("Failed to read file: %s", file), e);
    }
  }

}
