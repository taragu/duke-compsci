springies
=========


Starter code for Springies team project

### Inheritance Outline

In order to represent a spring-mass model and its environmnet, Class `Mass` and `Spring` will be implemented. They both extend Class `PhysicalObject`. We will also implement our walls by using the `PhysicalObjectRect` class, which extends `PhysicalObject`.

**Spring:** this class will contain the end points of the spring, connecting two masses, as well as a known resting length and a spring constant. We will have a `move()` method which will define how the spring changes when forces are applied. Due to the fact that this object will exert a force upon a `Mass` object, we will also implement hooke's law by writing a `calculateForce()` method.

**Spring.Muscle:** A muscle is just a spring, but with a changing resting length. So, the only difference between this class and its super (Spring) will be that there will be a `contractMuscle()` method that performs this resting length change.

**Mass:** This class will contain the objects that can be connected to other `Mass` objects by springs or muscles. Some traits we will keep track of in this class include: its mass, x location, y location, radius, and its assembly id (which will be used when adding multiple assemblies). There will be a `move()` method here which will deal with the various world forces that are exerted on the mass.

**Mass.Fixed:** This class extends `Mass`, but it overrides the `move()` method so that fixed objects cannot move. It will also have a different color as to depict that it is a object of type `Fixed` rather than `Mass`.

