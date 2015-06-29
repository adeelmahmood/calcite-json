package demo;

import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.junit.Test;

public class CalciteJsonApplicationTests {

	@Test
	public void testDriver() throws SQLException {
		Properties props = new Properties();
		Connection conn = DriverManager.getConnection("jdbc:calcite:", props);
		conn.close();
	}

	@Test
	public void findModel() {
		URL url = this.getClass().getResource("/model.json");
		System.out.println("url = " + url.toString());
	}

	@Test
	public void testModel() throws SQLException {
		Properties props = new Properties();
		props.put("model", getPath("/model.json"));
		System.out.println("model = " + props.get("model"));
		Connection conn = DriverManager.getConnection("jdbc:calcite:", props);

		DatabaseMetaData md = conn.getMetaData();
		ResultSet tables = md.getTables(null, "PEOPLE", "%", null);
		while (tables.next()) {
			System.out.println("--");
			System.out.println(tables.getString(1));
			System.out.println(tables.getString(2));
			System.out.println(tables.getString(3));
			System.out.println(tables.getString(4));
			
//			conn.createStatement().execute("select * from " + tables.getString(3));
			
			System.out.println("-->");
		}

		Statement stat = conn.createStatement();
		stat.execute("select _MAP['name'] from a");

		stat.close();
		conn.close();
	}

	private String getPath(String filePath) {
		URL url = this.getClass().getResource(filePath);
		String s = url.toString();
		return s.startsWith("file:") ? s.substring("file:".length()) : s;
	}

}
