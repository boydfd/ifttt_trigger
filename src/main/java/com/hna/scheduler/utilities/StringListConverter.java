package com.hna.scheduler.utilities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

  private static String DELIMITER = ",";

  @Override
  public String convertToDatabaseColumn(List<String> list) {
    return String.join(DELIMITER, list);
  }

  @Override
  public List<String> convertToEntityAttribute(String joined) {
    return new ArrayList<>(Arrays.asList(joined.split(DELIMITER)));
  }

}