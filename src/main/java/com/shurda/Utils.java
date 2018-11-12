package com.shurda;

import org.jsoup.nodes.Element;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    private static Map<String, String> getAllAttributes(Element element) {
        return element.attributes()
                .asList()
                .stream()
                .collect(Collectors.toMap(attribute -> attribute.getKey(), attribute -> attribute.getValue()));
    }

    public static List<String> checkAttr(Element original, Element compare) {
        Map<String, String> orig = Utils.getAllAttributes(original);
        Map<String, String> comp = Utils.getAllAttributes(compare);

        List<String> collect = orig
                .keySet()
                .stream()
                .filter(k -> comp.containsKey(k))
                .map(item -> {
                    String origValue = orig.get(item);
                    String compValue = comp.get(item);
                    String x = null;
                    if (origValue.equals(compValue)) {
                        x = "Attributes are equals with name:" + item + " and value: " + compValue;
                    }
                    return x;
                })
                .filter(v -> v != null)
                .collect(Collectors.toList());

        if (original.hasText() && compare.hasText() && original.text().equals(compare.text())) {
            collect.add("The text of elements is equals");
        }

        if (!collect.isEmpty()) {
            collect.add(0, "Path to diff elements: " + Utils.buildPath(compare));
        }

        return collect;

    }


    public static String buildPath(Element element) {
        final List<String> path = element.parents()
                .stream()
                .map(Utils::formatPath)
                .collect(Collectors.toList());

        Collections.reverse(path);
        return path.stream()
                .collect(Collectors.joining(" > "));
    }

    private static String formatPath(Element element) {
        return String.format("%s[%s] ", element.tagName(), element.elementSiblingIndex());
    }
}
