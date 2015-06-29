package demo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.calcite.linq4j.Enumerator;
import org.apache.calcite.linq4j.Linq4j;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonEnumerator implements Enumerator<Object[]> {

	private Enumerator<Object> enumerator;

	@Autowired
	public JsonEnumerator(File file) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

		List<Object> list;
		try {
			list = mapper.readValue(file, new TypeReference<List<Object>>() {
			});
			System.out.println("records count " + list.size());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		enumerator = Linq4j.enumerator(list);
	}

	@Override
	public Object[] current() {
		return (Object[]) enumerator.current();
	}

	@Override
	public boolean moveNext() {
		return enumerator.moveNext();
	}

	@Override
	public void reset() {
		enumerator.reset();
	}

	@Override
	public void close() {
		enumerator.close();
	}

}
