package com.shurda;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;


public class JsoupFindByIdSnippet {

    private static Logger LOGGER = Logger.getAnonymousLogger();

    private static String CHARSET_NAME = "utf8";

    public static void main(String[] args) {

        String targetElementId = "make-everything-ok-button";
        String resourcePath;
        String comparePath;

        switch (args.length) {
            case 3:
                targetElementId = args[2];
            case 2:
                resourcePath = args[0];
                comparePath = args[1];
                break;
            default:
                System.out.println("Format input parameters: <input_origin_file_path> <input_other_sample_file_path> <optional - element ID>");
                return;

        }

        Optional<Element> buttonOpt = findElementById(new File(resourcePath), targetElementId);
        Elements elementsByTag = findElementsByTag(new File(comparePath), buttonOpt.get().tagName());

        if (elementsByTag.isEmpty()) {
            System.out.println("In second file does not contains equals elements");
            return;
        }

        for (Element element : elementsByTag) {
            Utils.checkAttr(buttonOpt.get(), element)
                    .forEach(System.out::println);
        }
    }

    private static Elements findElementsByTag(File htmlFile, String tag) {
        Elements elements = new Elements();
        try {
            Document doc = getDocument(htmlFile);
            elements = doc.getElementsByTag(tag);

        } catch (IOException e) {
            LOGGER.severe("Error reading " + htmlFile.getAbsolutePath() + " file");
        }
        return elements;
    }

    private static Optional<Element> findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = getDocument(htmlFile);
            return Optional.of(doc.getElementById(targetElementId));
        } catch (IOException e) {
            LOGGER.severe("Error reading " + htmlFile.getAbsolutePath() + " file");
            return Optional.empty();
        }
    }

    private static Document getDocument(File htmlFile) throws IOException {
        return Jsoup.parse(
                htmlFile,
                CHARSET_NAME,
                htmlFile.getAbsolutePath());
    }
}