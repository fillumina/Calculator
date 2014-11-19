# Calculator

An easy to configure, flexible, reasonably fast calculator.

* It parses a string expression according to the rules given by a costumized
grammar (included are a boolean grammar, an arithmetic grammar based on doubles
and an arithmetic grammar based on BigDecimal) and produces a solution.
* It has two kind of solvers (a stright one and a simplifying one) that can be
used to search and solve for incognita variables in the expression iteratively.
* It has a grammar builder that helps building the custom grammars in a very
easy and intuitive way.
* And at last is reasonably fast to use.

To get a glipse of how to use its API take a look at the tutorial unit test:
[ATutorialTest.java]
(/src/test/java/com/fillumina/calculator/ATutorialTest.java).