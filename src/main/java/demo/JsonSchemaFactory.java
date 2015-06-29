package demo;

import java.io.File;
import java.util.Map;

import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;

public class JsonSchemaFactory implements SchemaFactory {

	public JsonSchemaFactory() {
	}

	@Override
	public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
		final String directory = (String) operand.get("directory");
		return new JsonSchema(new File(directory));
	}

}
