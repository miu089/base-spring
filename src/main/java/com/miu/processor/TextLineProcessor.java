package com.miu.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.standard.processor.AbstractStandardExpressionAttributeTagProcessor;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.util.LazyEscapingCharSequence;
import org.unbescape.html.HtmlEscape;
import org.unbescape.xml.XmlEscape;

/**
 * 改行コードをbrタグに変換するプロセッサ
 */
public class TextLineProcessor extends AbstractStandardExpressionAttributeTagProcessor {
  private static final String ATTR_NAME = "textbr";

  public TextLineProcessor(String dialectPrefix, int precedence) {
    super(TemplateMode.HTML, dialectPrefix, ATTR_NAME, precedence, true);
  }

  @Override
  protected void doProcess(ITemplateContext context,
                           IProcessableElementTag tag,
                           AttributeName attributeName,
                           String attributeValue,
                           Object expressionResult,
                           IElementTagStructureHandler structureHandler) {
    // nullの場合は何も出力せず処理を終了
    if (expressionResult == null) {
      return;
    }

    TemplateMode templateMode = this.getTemplateMode();
    Object text;
    if (templateMode != TemplateMode.JAVASCRIPT && templateMode != TemplateMode.CSS) {
      String input = expressionResult == null ? "" : expressionResult.toString();
      if (templateMode == TemplateMode.RAW) {
        text = input;
      } else if (input.length() > 100) {
        text = new LazyEscapingCharSequence(context.getConfiguration(), templateMode, input);
      } else {
        text = produceEscapedOutput(templateMode, input);
      }
    } else {
      text = new LazyEscapingCharSequence(context.getConfiguration(), templateMode, expressionResult);
    }

    text = text.toString().replaceAll("\r\n|\r|\n", "<br/>");

    structureHandler.setBody((CharSequence) text, false);
  }

  private static String produceEscapedOutput(TemplateMode templateMode, String input) {
    switch (templateMode) {
      case TEXT:
      case HTML:
        return HtmlEscape.escapeHtml4Xml(input);
      case XML:
        return XmlEscape.escapeXml10(input);
      default:
        throw new TemplateProcessingException("Unrecognized template mode " + templateMode + ". Cannot produce escaped output for this template mode.");
    }
  }


}
