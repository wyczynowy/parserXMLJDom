package app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class Main {

	public static void main(String[] args) {
		Document dokument = wczytajDokumentXML("C:/EXAMPLES/example.xml");
		Element root = dokument.getRootElement();
		wyswietlInfoOElemencie(root, 1);
		zapiszDokumentWPliku(dokument, "C:/EXAMPLES/example_out.xml", true);
	}

	/**
	 * Wczytuje dokument xml ze wskazanej lokalizacji
	 * 
	 * @param sciezkaDoPlikuXML
	 * @return
	 */
	public static Document wczytajDokumentXML(String sciezkaDoPlikuXML) {
		try {
			// Nie sprawdzamy poprawności przy budowaniu dokumentu
			SAXBuilder builder = new SAXBuilder(false);
			Document dokument = builder.build(new File(sciezkaDoPlikuXML));

			return dokument;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Zapisuje dokument do wskazanej lokalizacji
	 * 
	 * @param dokument
	 * @param sciezkaDoPliku
	 * @param czyZamknacElement
	 * @return
	 */
	public static boolean zapiszDokumentWPliku(Document dokument, String sciezkaDoPliku, boolean czyZamknacElement) {
		boolean status = true;
		OutputStreamWriter outputStreamWriter = null;
		try {
			outputStreamWriter = new OutputStreamWriter(new FileOutputStream(sciezkaDoPliku), "UTF-8");
			Format format = Format.getRawFormat();
			format.setExpandEmptyElements(czyZamknacElement);
			XMLOutputter outp = new XMLOutputter(format);
			outp.output(dokument, outputStreamWriter);
		} catch (IOException ioe) {
			status = false;
		} finally {
			try {
				if (outputStreamWriter != null)
					outputStreamWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return status;
	}

	private static void wyswietlInfoOElemencie(Element element, int poziom) {
		System.out.print(wyswietlPoziom(poziom) + "<" + element.getName());
		@SuppressWarnings({ "unchecked" })
		List<Attribute> atrybutyElementu = element.getAttributes();
		for (int i = 0; i < atrybutyElementu.size(); i++) {
			System.out.print(" " + atrybutyElementu.get(i).getName() + "='" + atrybutyElementu.get(i).getValue() + "'");
		}

		System.out.println(">");
		if (element.getText().trim().length() > 0)
			System.out.println(wyswietlPoziom(poziom + 1) + element.getText().trim());

		if (element.getChildren().size() > 0) {
			@SuppressWarnings("unchecked")
			List<Element> dzieciElementu = element.getChildren();
			for (Element dziecko : dzieciElementu) {
				wyswietlInfoOElemencie(dziecko, poziom + 2);
			}
		}

		System.out.println(wyswietlPoziom(poziom) + "</" + element.getName() + ">");
	}

	private static String wyswietlPoziom(int poziom) {
		String odstep = "";
		for (int i = 0; i < poziom; i++) {
			odstep += " ";
		}
		return odstep;
	}

}
