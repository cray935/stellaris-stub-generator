package de.stellaris;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import de.stellaris.domain.technology.ReaderException;
import de.stellaris.domain.technology.Technology;
import de.stellaris.domain.technology.TechnologyReader;
import de.stellaris.domain.technology.TechnologyWriter;
import de.stellaris.domain.technology.WriterException;
import de.stellaris.infrastructure.TechnologyReaderJson;
import de.stellaris.infrastructure.TechnologyWriterGiveTechStub;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleApp {

  private static final String DATA_ROOT = "stellaris_data";
  private static final String INPUT_DIR = "json_tech";
  private static final String OUTPUT_DIR = "stubs";
  private static final String OUTPUT_FILE_PREF = "give_techs-";
  private static final TechnologyReader READER_SERVICE = new TechnologyReaderJson();
  private static final TechnologyWriter WRITER_SERVICE = new TechnologyWriterGiveTechStub();

  public static void main(String[] args) throws IOException {

    Path input = Paths.get(System.getProperty("user.home"), DATA_ROOT, INPUT_DIR);
    Path output = Paths.get(System.getProperty("user.home"), DATA_ROOT, OUTPUT_DIR);

    Files.list(input).forEach(txt -> {
      String fileName = txt.getFileName().toString();
      log.info("Processing file: {}", fileName);
      try {
        List<Technology> techs = READER_SERVICE.readTechnologies(txt);
        Path json = Path.of(output.toString(), OUTPUT_FILE_PREF.concat(fileName.replaceAll("\\.json", ".txt")));
        WRITER_SERVICE.writeTechnologies(json, techs);
      } catch (ReaderException | WriterException e) {
        log.error("Failed to write stubs for file: {}", txt, e);
      }
    });
  }
}
