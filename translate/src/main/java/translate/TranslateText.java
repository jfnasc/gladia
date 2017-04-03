package translate;

import java.io.PrintStream;
import java.util.Optional;

import com.google.cloud.RetryParams;
import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.LanguageListOption;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.common.collect.ImmutableList;

public class TranslateText {
	/**
	 * Detect the language of input text.
	 *
	 * @param sourceText
	 *            source text to be detected for language
	 * @param out
	 *            print stream
	 */
	public static void detectLanguage(String sourceText, PrintStream out) {
		Translate translate = createTranslateService();
		java.util.List<Detection> detections = translate.detect(ImmutableList.of(sourceText));
		System.out.println("Language(s) detected:");
		for (Detection detection : detections) {
			out.printf("\t%s\n", detection);
		}
	}

	/**
	 * Translates the source text in any language to English.
	 *
	 * @param sourceText
	 *            source text to be translated
	 * @param out
	 *            print stream
	 */
	public static void translateText(String sourceText, PrintStream out) {
		Translate translate = createTranslateService();
		Translation translation = translate.translate(sourceText);
		out.printf("Source Text:\n\t%s\n", sourceText);
		out.printf("Translated Text:\n\t%s\n", translation.getTranslatedText());
	}

	/**
	 * Translate the source text from source to target language. Make sure that
	 * your project is whitelisted.
	 *
	 * @param sourceText
	 *            source text to be translated
	 * @param sourceLang
	 *            source language of the text
	 * @param targetLang
	 *            target language of translated text
	 * @param out
	 *            print stream
	 */
	public static void translateTextWithOptionsAndModel(String sourceText, String sourceLang, String targetLang,
			PrintStream out) {

		Translate translate = createTranslateService();
		TranslateOption srcLang = TranslateOption.sourceLanguage(sourceLang);
		TranslateOption tgtLang = TranslateOption.targetLanguage(targetLang);

		// Use translate `model` parameter with `base` and `nmt` options.
		TranslateOption model = TranslateOption.model("nmt");

		Translation translation = translate.translate(sourceText, srcLang, tgtLang, model);
		out.printf("Source Text:\n\tLang: %s, Text: %s\n", sourceLang, sourceText);
		out.printf("TranslatedText:\n\tLang: %s, Text: %s\n", targetLang, translation.getTranslatedText());
	}

	/**
	 * Translate the source text from source to target language.
	 *
	 * @param sourceText
	 *            source text to be translated
	 * @param sourceLang
	 *            source language of the text
	 * @param targetLang
	 *            target language of translated text
	 * @param out
	 *            print stream
	 */
	public static String translateTextWithOptions(String sourceText, String sourceLang, String targetLang) {

		Translate translate = createTranslateService();
		TranslateOption srcLang = TranslateOption.sourceLanguage(sourceLang);
		TranslateOption tgtLang = TranslateOption.targetLanguage(targetLang);

		Translation translation = translate.translate(sourceText, srcLang, tgtLang);
		return translation.getTranslatedText();
	}

	/**
	 * Displays a list of supported languages and codes.
	 *
	 * @param out
	 *            print stream
	 * @param tgtLang
	 *            optional target language
	 */
	public static void displaySupportedLanguages(PrintStream out, Optional<String> tgtLang) {
		Translate translate = createTranslateService();
		LanguageListOption target = LanguageListOption.targetLanguage(tgtLang.orElse("en"));
		java.util.List<Language> languages = translate.listSupportedLanguages(target);

		for (Language language : languages) {
			out.printf("Name: %s, Code: %s\n", language.getName(), language.getCode());
		}
	}

	/**
	 * Create Google Translate API Service.
	 *
	 * @return Google Translate Service
	 */
	public static Translate createTranslateService() {
		TranslateOptions translateOption = TranslateOptions.newBuilder()
				.setApiKey("AIzaSyBEu0v0ZoGqTVJEL9Vy8oqgQpRlEB-03rc").setRetryParams(retryParams())
				.setConnectTimeout(60000).setReadTimeout(60000).build();
		return translateOption.getService();
	}

	/**
	 * Retry params for the Translate API.
	 */
	private static RetryParams retryParams() {
		return RetryParams.newBuilder().setRetryMaxAttempts(3).setMaxRetryDelayMillis(30000)
				.setTotalRetryPeriodMillis(120000).setInitialRetryDelayMillis(250).build();
	}

	public static String translate(String text) {

		String result = "";
		final String sourceLang = "en";
		final String targetLang = "pt";

		try {
			result = TranslateText.translateTextWithOptions(text, sourceLang, targetLang);
		} catch (ArrayIndexOutOfBoundsException ex) {
			TranslateText.translateText(text, System.out);
		}

		return result;
	}
}
