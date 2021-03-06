# Calculator

An easy to configure, flexible, reasonably fast Java calculator API.

* Parses a string expression according to the rules given by a
grammar (includes a boolean grammar, an arithmetic grammar based on doubles
and an arithmetic grammar based on BigDecimals) and calculates a solution;
* Has the possibility to simplify an expression and solve incognita
variables in the expression iteratively;
* Has a grammar builder that helps building your own custom grammars in
a very easy and intuitive way;
* And, at last, it's reasonably fast.

To get a glipse of how to use it take a look at the tutorial unit test:
[ATutorialTest.java]
(/src/test/java/com/fillumina/calculator/ATutorialTest.java).


## Version

This is its first public release so it still has the 1.0-SNAPSHOT version tag.
After a little bit of refiniment here it will be promoted to 1.0 and uploaded
to maven central for general avaliability.


## Using the provided grammars to configure a ready to use calculator

Using one of the three provided grammars it is possible to build a
calculator capable to parse expressions with whitespaces, infinite order of
nested parentheses and settable variables. These are the provided grammars:
* Double arithmetic;
* BigDecimal arithmetic (some operations use double arithmetic);
* Boolean arithmetic.

This is an example of how to create and use a simple grammar:

```java
    final Calculator<Double,Map<String,Double>> calculator =
            new Calculator<>(DoubleArithmeticGrammar.INSTANCE);
    assert 12.5 == calculator.solveSingleValue("(20 + 5)/2 ");
```

A string can contain more than one expression (i.e. ```"2 2+2"```) and the
solution will be a list of solutions (i.e. ```[2 4]```). If you are
interested in the first solution only you can use the ```solveSingleValue()```
method.

This example shows how to use variables:
```java
    final MappedContextSimplifyingCalculator<Double> calc =
        new MappedContextSimplifyingCalculator<>(DoubleArithmeticGrammar.INSTANCE);

    // Mapper is an helper to easily create and set maps of type Map<String,T>
    Map<String,Double> context = Mapper.<Double>create("hundred", 100.0);

    assert 121 == calc.solveSingleValue(context, "(x = 15 + hundred) + 6");

    assert 115 == context.get("x");
```

## Creating your own grammar

The calculator is extremely flexible and easy to configure and modify.
You can create your own customized components using the constructors
and implementing the interfaces. But it's by far easier to customize it by
creating your own grammar (or maybe extending the provided ones)
using a builder:

```java
    // creates a custom grammar
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

Have fun! (and possibly let me know about what do you think ;) )