package Exercice3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class StringFilterTest {

	static String[] filter(String[] strings, StringFilter filter) {
		String[] filtered_strings = new String[strings.length];
		for (int i = 0; i < strings.length; ++i) {
			filtered_strings[i] = filter.filter(strings[i]);
		}
		return filtered_strings;
	}

	@Test
	public void upperCaseStringFilter() {
		String input = "aBcD";
		StringFilter filter = new UpperCaseStringFilter();
		String output = filter.filter(input);
		assertThat(output, equalTo("ABCD"));
	}

	@Test
	public void lowerCaseStringFilter() {
		String input = "aBcD";
		StringFilter filter = new LowerCaseStringFilter();
		String output = filter.filter(input);
		assertThat(output, equalTo("abcd"));
	}

	@Test
	public void prefixStringFilter() {
		String input = "abcd";
		StringFilter filter = new PrefixStringFilter(2);
		String output = filter.filter(input);
		assertThat(output, equalTo("ab"));
	}

	@Test
	public void postfixStringFilter() {
		String input = "abcd";
		StringFilter filter = new PostfixStringFilter(2);
		String output = filter.filter(input);
		assertThat(output, equalTo("cd"));
	}

	@Test
	public void asciiStringFilter() {
		String input = "àabïcîdé";
		StringFilter filter = new AsciiStringFilter();
		String output = filter.filter(input);
		assertThat(output, equalTo("abcd"));
	}

	@Test
	public void compositeStringFilter() {
		String input = "àabïcîdé";
		StringFilter[] filters = { new AsciiStringFilter(), new UpperCaseStringFilter() };
		StringFilter filter = new CompositeStringFilter(filters);
		String output = filter.filter(input);
		assertThat(output, equalTo("ABCD"));
	}

	@Test
	public void testFilter() {
		String[] strings = { "ABCD", "AbCd", "abcD" };
		StringFilter filter = new LowerCaseStringFilter();

		String[] result = filter(strings, filter);

		assertThat(result[0], equalTo("abcd"));
		assertThat(result[1], equalTo("abcd"));
		assertThat(result[2], equalTo("abcd"));
	}
}
