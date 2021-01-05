# QuadTree Particle System
A particle system that dynamically splits into sections to reduce individual checks for collisions.
* This is the summative for the second unit of ICS4UE, demonstrating the implementation and use of basic data structures, particularly linked lists and trees
* Particles will collide off each other and walls
* When six or more particles exist in a section, the section will subdivide into quadrants
* Each subdivided quadrant is also able to subdivide into quadrants if it passes the threshold of six particles
* If a section has already subdivided but no longer contains six or more particles, the four subdivided quadrants will collapse back into one section
* Collisions between particles are checked only in the smallest subdivided quadrants, which are the leaves of the quadtree