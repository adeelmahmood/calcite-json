package demo;

import java.io.File;
import java.util.Map;

import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

public class JsonSchema extends AbstractSchema {

	private static final Logger log = LoggerFactory.getLogger(JsonSchema.class);

	private final File directory;

	public JsonSchema(File directory) {
		this.directory = directory;
	}

	@Override
	protected Map<String, Table> getTableMap() {
		// get all files in directory
		File[] files = directory.listFiles((dir, name) -> {
			return name.endsWith("json");
		});
		if (files == null) {
			log.warn("no files were found in directory " + directory);
			files = new File[0];
		}

		final ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();
		// for each file create a json table
		for (File file : files) {
			String tableName = file.getName().substring(0, file.getName().indexOf("."));
			System.out.println("created table for " + tableName + " from file " + file.getName());
			builder.put(tableName, new JsonTable(file));
		}

		return builder.build();
	}
}
