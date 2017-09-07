package vn.tiki.architecture.util;

import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by Giang Nguyen on 9/7/17.
 */
public class EmailValidatorTest {
  private EmailValidator tested;

  @Before
  public void setUp() throws Exception {
    tested = new EmailValidator();
  }

  @Test
  public void testValidate_invalidEmail() throws Exception {
    assertThat(tested.validate("foo")).isFalse();
    assertThat(tested.validate("foo@")).isFalse();
    assertThat(tested.validate("foo@gmail")).isFalse();
  }

  @Test
  public void testValidate_validEmail() throws Exception {
    assertThat(tested.validate("foo@gmail.com")).isTrue();
  }
}