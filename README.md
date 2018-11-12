Run program:

java -jar smart-xml-analyzer.jar <input_origin_file_path> <input_other_sample_file_path>  <optional - element ID>

Write a program that analyzes HTML and finds a specific element, even after changes, using a set of extracted attributes. We’ve prepared a sample HTML page (“original” below) and 4 simple difference cases: first, second, third, fourth (“diff-case” below) for you ( download as a single pack ). Please open the pages in browser to see what we mean by minor website changes. The target element that needs to be found by your program is the green “Everything OK” button. Any user can easily find this button visually, even when the site changes. Original contains a button with attribute id="make-everything-ok-button". This id is the only exact criteria, to find the target element in the input file. See example below.

