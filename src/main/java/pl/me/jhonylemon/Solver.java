package pl.me.jhonylemon;

import pl.me.jhonylemon.vial.Vials;

/**
 * Abstract class representing a solver for the Vials game.
 */
public abstract class Solver {
    /**
     * Solves the Vials game using a specific algorithm.
     * @param vials The initial state of the vials.
     * @return The last node of the solution tree.
     */
    public abstract Node solve(Vials vials);
}
