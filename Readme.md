#**Jenetic Algorithm**


Jenetic Algorithm is a small Java library for [Genetic Algorithms](https://en.wikipedia.org/wiki/Genetic_algorithm).
It's main goal is to provide an easy to use implementation of the Genetic Algorithm, while still exploiting the typical parallelism this search heuristic allows and allowing the customization need to solve the most specific problems.


###**Easy to use**

In the design of the library there was no constraint on Chromosome. You can use any class you want, without the need to extend a given class or implement an interface. This way, you are free to use classes from external libraries you can not modify.

To start using it, you just need to provide:

 - The initial population
 - The fitness function
 - The genetic operators

The library provides choices for the selection algorithm and the end condition, but you can implement your flavor of them. 

####**Initial Population**
Initialize (usually randomly) your chromosome instances to provide a population from which to start the evolution process. This needs to implement the  ```Collection<Chromosome>``` interface, where chromosome is the class you'll use as chromosome.

####**Fitness Function**
Provide a function which, given a chromosome, returns it's fitness: how good a solution it is. You can do so by implementing the ```Evaluation``` interface.

####**Selection Function**
This function specifies how two chromosomes are picked from the current population to generate a new offspring. At the moment the library provides two implementations:

 - Roulette Wheel Selection
 - Rank Selection

If your specific problem needs a different selection strategy you can implement it by providing a ```Selection``` implementation.
The default is Rank Selection.

####**Genetic Operators: Crossover**
This function will get two chromosomes as input and return a new chromosome. Define here how a new offspring is generated from two parents. Implement the ```Crossover``` interface.
**NOTE:** remember to set the crossover probability when you define the problem! It's usually in the 80%-90% range.

####**Genetic Operators: Mutation**
A single chromosome may need to be mutated. This function specifies how that happens. It takes a chromosome and returns a chromosome which is the mutation of the input one. Implement the ```Mutate``` interface.
**NOTE:** remember to set the mutate probability when you define the problem! It's usually in the 5%-20% range.

####**End Condition**
The algorithm need to know when to stop. In this case the library provides two implementations:

- Generation Condition: stop after N generations.
- Fitness Condition: stop when the best chromosome has a fitness greater or equal to a threshold.

The default is Generation Condition with a N equal to 1000.

####**Elitism**
Elitism consist on adding the best chromosomes from the previous population to the next one. You can set it during the problem definition. By default it is disabled (set to 0), but you can set the number of chromosomes to bring to the next generation.
**NOTE:** high values compared to the size of the population will make the algorithm converge slower or stop on local optimals.


----------

###**Problem Definition**
Once you implemented all the needed parts, you can use the ```ProblemDescription``` class to put together the classes you just wrote, and instantiate a ```GeneticAlgorithm```.
From there it is as simple as calling ```run()``` and then ```getBest()``` to obtain your solution.

###**Examples**
The library comes with an example to show how to use it. It is the classic [knapsack problem](https://en.wikipedia.org/wiki/Knapsack_problem).
From its main you can see how easy it is to use the library.

```java
ProblemDescription<KnapsackChromosome> problem = new ProblemDescription<KnapsackChromosome>();
		KnapsackEvalCrossMutate evaluateCrossoverMutate = new KnapsackEvalCrossMutate();
		problem.mCrossoverFunction = evaluateCrossoverMutate;
		problem.mEvaluationFunction = evaluateCrossoverMutate;
		problem.mMutateFunction = evaluateCrossoverMutate;
		problem.mInitialPopulation = KnapsackChromosome.initialPopulation(20);
		problem.mCrossoverProbability = 0.8f;
		problem.mMutateProbability = 0.15f;
		problem.mElitismNumber = 3;

		GeneticAlgorithm<KnapsackChromosome> solver = new GeneticAlgorithm<KnapsackChromosome>(problem);
		solver.run();
		FitnessEntry<KnapsackChromosome> best = solver.getBest();
```
