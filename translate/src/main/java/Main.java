import com.google.api.services.translate.Translate;

public class Main {
	public static void main(String[] args) throws Exception {
	
		// Replace link with the HTTP referrer to your website address
		GoogleAPI.setHttpReferrer("link");

		// Replace key with the Google Translate API key
		GoogleAPI.setKey("key");

		// Do the translation
		String translatedText = Translate.DEFAULT.execute("Hello world", Language.ENGLISH, Language.FRENCH);

		System.out.println(translatedText);
	}
}