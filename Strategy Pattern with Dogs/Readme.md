# Strategy Pattern

Here we try out the strategy pattern on dogs. We create several kinds of dogs:
 - Husky
 - RobotDog
 - ToyDog
 
 All of them inherit from an abstract class Dog(). This class defines two behaviors that we extract to separate classes and use Composition to make Dog() use them:
 - barkBehavior, and
 - runBehavior
 
 We use interfaces BarkBehavior() and RunBehavior() so that Dog() only knows about them and _not_ the concrete implementations.
 
 Since Dog() knows about the Interfaces, it _can_ do the work of delegating the bark() and run() calls to the (composite) members. In other words, from the interfaces, Dog() knows how to use their respective implementations, but does not care which one it gets. This is also the "magic" of Polymorphism.
 
 
In Main() we also profit from Polymorphism because we only care that we have Dog()'s when we call methods on them. With the setter methods, we can even swap out a certain behavior with another one during runtime, which is exactly what the "Wizard" does to give ToyDog the ability to run and bark.


Git Log (issue): https://github.com/eucalypto/learn/issues/10