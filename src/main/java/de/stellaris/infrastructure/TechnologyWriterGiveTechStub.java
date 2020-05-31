package de.stellaris.infrastructure;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

import de.stellaris.domain.technology.Technology;
import de.stellaris.domain.technology.TechnologyWriter;
import de.stellaris.domain.technology.WriterException;

public class TechnologyWriterGiveTechStub implements TechnologyWriter {

  private static final String STUB_FORMAT = "give_technology = { tech = %s message = no }";

  @Override
  public void writeTechnologies(Path output, List<Technology> technologies) throws WriterException {

    try {
      doWrite(output, technologies);
    } catch (FileNotFoundException e) {
      throw new WriterException(String.format("Could not write techs to file: %s", output), e);
    }
  }

  private void doWrite(Path output, List<Technology> technologies) throws FileNotFoundException {

    try (PrintWriter out = new PrintWriter(output.toFile())) {

      for (Technology tech : technologies) {
        out.printf(STUB_FORMAT, tech.getId());
        out.println();
      }
    }
  }
}
