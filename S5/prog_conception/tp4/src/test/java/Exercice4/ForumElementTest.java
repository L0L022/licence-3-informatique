package Exercice4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ForumElementTest {

	private ByteArrayOutputStream outContent;

	@Before
	public void setUpStream() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStream() {
		System.setOut(null);
	}

	@Test
	public void test() {
		Topic topic1 = new Topic("Premier topic");
		topic1.addElement(new Message("Message 1"));
		topic1.addElement(new Message("Message 2"));

		Topic topic2 = new Topic("Deuxième topic");
		topic2.addElement(new Message("Message 3"));

		Forum forum = new Forum();
		forum.addElement(topic1);
		forum.addElement(topic2);

		forum.print();

		String expectedOutput = "Forum :\n    Topic : Premier topic\n        Message 1\n        Message 2\n    Topic : Deuxième topic\n        Message 3\n";
		assertThat(outContent.toString(), equalTo(expectedOutput));
	}

}
