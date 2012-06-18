package com.github.edgarespina.handlerbars.internal;

import java.io.IOException;

import com.github.edgarespina.handlerbars.Handlebars;
import com.github.edgarespina.handlerbars.Lambda;
import com.github.edgarespina.handlerbars.Template;

/**
 * Utilities function for work with lambdas.
 *
 * @author edgar.espina
 * @since 0.1.0
 */
final class Lambdas {

  /**
   * Merge the lambda result.
   *
   * @param handlebars The handlebars.
   * @param lambda The lambda.
   * @param scope The current scope.
   * @param template The current template.
   * @return The resulting text.
   * @throws IOException
   */
  public static String merge(final Handlebars handlebars,
      final Lambda<Object, Object> lambda, final Context scope,
      final Template template) throws IOException {
    BaseTemplate result = compile(handlebars, lambda, scope, template);
    return result.apply(scope);
  }

  /**
   * Compile the given lambda.
   *
   * @param handlebars The handlebars.
   * @param lambda The lambda.
   * @param scope The current scope.
   * @return The resulting template.
   * @throws IOException
   */
  public static BaseTemplate compile(final Handlebars handlebars,
      final Lambda<Object, Object> lambda, final Context scope,
      final Template template)
      throws IOException {
    return compile(handlebars, lambda, scope, template, "{{", "}}");
  }

  /**
   * Compile the given lambda.
   *
   * @param handlebars The handlebars.
   * @param lambda The lambda.
   * @param scope The current scope.
   * @param delimStart The start delimiter.
   * @param delimEnd The end delimiter.
   * @return The resulting template.
   * @throws IOException
   */
  public static BaseTemplate compile(final Handlebars handlebars,
      final Lambda<Object, Object> lambda, final Context scope,
      final Template template, final String delimStart, final String delimEnd)
      throws IOException {
    Object value = lambda.apply(scope, template);
    BaseTemplate result;
    if (value instanceof CharSequence) {
      result =
          (BaseTemplate) handlebars.compile(value.toString(), delimStart,
              delimEnd);
    } else {
      result = new Variable(handlebars, "$$lambda", value, false);
    }
    return result;
  }
}