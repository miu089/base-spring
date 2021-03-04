package com.miu.dialect;


import com.miu.processor.TextLineProcessor;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

/** thymeleafで改行表示するためのDialectの設定 */
public class TextLineDialect extends AbstractProcessorDialect {

  private static final String NAME = "original dialect for auto textbr";
  private static final String PREFIX = "ex";

  public TextLineDialect() {
    super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
  }

  @Override
  public Set<IProcessor> getProcessors(String dialectPrefix) {
    Set<IProcessor> proccessors = new HashSet<>();

    proccessors.add(new TextLineProcessor(dialectPrefix, getDialectProcessorPrecedence()));

    return proccessors;
  }

}
