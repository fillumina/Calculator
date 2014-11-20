# Calculator

An easy to configure, flexible, reasonably fast calculator API.

> Do you need an easy way to create your own calculator in Java?
> Ever wanted to add that strange function nobody cares about?
> This is a very easy to configure and extend calculator API.
> It can process strings and produce any type of solution you need.

* It parses a string expression according to the rules given by a
grammar (includes a boolean grammar, an arithmetic grammar based on doubles
and an arithmetic grammar based on BigDecimal) and produces a solution.
* It has the possibility to simplify an expression and solve incognita
variables in the expression iteratively.
* It has a grammar builder that helps building your own custom grammars in
a very easy and intuitive way.
* And at last is reasonably fast to use.

To get a glipse of how to use its API take a look at the tutorial unit test:
[ATutorialTest.java]
(/src/test/java/com/fillumina/calculator/ATutorialTest.java).


## Using the provided grammars to configure a ready to use calculator

Using one of the three provided grammars it is possible to build a working
calculator capable to parse expressions with whitespaces, infinite order of
nested parentheses and settable variables. These are the provided grammars:
* Double arithmetic;
* BigDecimal arithmetic (some operations use double arithmetic);
* Boolean arithmetic.

This is an example of how to create and use a simple grammar:

```java
    final Calculator<Double,Map<String,Double>> calculator =
            new Calculator<>(ArithmeticGrammar.INSTANCE);
    assert 12.5 == calculator.solveSingleValue("(20 + 5)/2 ");
```

A string can contain more than one expression (i.e. ```"2 2+2"```) and the
solution will be a list of solutions (i.e. ```[2 4]```). If you are
interested in the first solution only you can use the ```solveSingleValue()```
method.

This example shows how to use variables:
```java
    final MappedContextSimplifyingCalculator<Double> calc =
            new MappedContextSimplifyingCalculator<>(ArithmeticGrammar.INSTANCE);

    // Mapper is an helper to easily create and set maps of type Map<String,T>
    Map<String,Double> context = Mapper.<Double>create("hundred", 100.0);

    assert 121 == calc.solveSingleValue(context, "(x = 15 + hundred) + 6");

    assert 115 == context.get("x");
```

## Creating your own grammar

The calculator is extremely flexible and easy to configure and modify by
creating your own components using the constructors and implementig the
interfaces but the most useful way of doing so is by creating your
own grammar (or maybe extending the provided ones) using a builder:

```java
    // creates a calculator with a custom grammar
    Iterable<GrammarElement<Integer,Map<String,Integer>>> grammar =
        new ContextedGrammarBuilder<Integer>()
            // creates the operand
            .addIntegerOperand(new Evaluator
                        <Integer, Map<String,Integer>>() {
                    @Override
                    public Integer evaluate(String value,
                            Map<String,Integer> context) {
                        return Integer.valueOf(value);
                    }
                })

            // defines a constant
            .addConstant("hundred", 100)

            // defines the + operator
            .addOperator()
                .priority(1)
                .operandsBefore(1)
                .operandsAfter(1)
                .symbols("+")
                .evaluator(new ParametricEvaluator
                            <Integer, Map<String,Integer>>() {
                        @Override
                        public Integer evaluate(String value,
                                List<Integer> params,
                                Map<String,Integer> context) {
                            return params.get(0) + params.get(1);
                        }
                    })
                .buildOperator()

            // creates a grammar with settable variables, white spaces
            // and parentheses
            .buildDefaultGrammarWithSettableVariables());
```

Have fun! (and possibly let me know about what you think ;) )