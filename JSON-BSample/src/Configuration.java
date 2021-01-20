import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

public class Configuration {

	public String workshop;
	public int attendees;

	private final static String CONFIGURATION_FILE = "airhacks-config.json";

	public Configuration save() {
		Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
		try (FileWriter writer = new FileWriter(CONFIGURATION_FILE)) {
			jsonb.toJson(this, writer);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return this;

	}

	public static Configuration load() {
		if (!Files.exists(Paths.get(CONFIGURATION_FILE))) {
			return new Configuration().save();
		}
		try (FileReader reader = new FileReader(CONFIGURATION_FILE)) {
			return JsonbBuilder.create().fromJson(reader, Configuration.class);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}

	public static void main(String args[]) {
		System.out.println("Begin:");
		Configuration configuration = Configuration.load();
		configuration.attendees = 25;
		configuration.workshop = "Cloudy Jakarta EE";
		configuration.save();
	}
}