package pl.me.jhonylemon;

import pl.me.jhonylemon.vial.Vials;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Depth First Search (DFS) algorithm for solving the Vials game.
 * This class extends the Solver class and implements the solve method.
 */
public class DepthFirstSearch extends Solver {

    private final ForkJoinPool pool = new ForkJoinPool();

    /**
     * Solves the Vials game using the Depth First Search algorithm. Finds the node that has all the vials filled with the same color.
     * {@inheritDoc}
     */
    @Override
    public Node solve(Vials vials) {
        long startTime = System.nanoTime();

        AtomicBoolean solutionFound = new AtomicBoolean(false);
        Node root = new Node(null, null, 0L);
        Node outcome = pool.invoke(new Task(root, vials.copy(), solutionFound));

        long endTime = System.nanoTime();
        Duration duration = Duration.ofNanos(endTime - startTime);
        System.out.printf("Time taken: %sh %sm %ss %sms %sns %n",
                duration.toHoursPart(),
                duration.toMinutesPart(),
                duration.toSecondsPart(),
                duration.toMillisPart(),
                duration.toNanosPart()
        );

        return outcome;
    }


    private boolean isSolution(Deque<Node> stack, Vials vials) {
        Node currentRoot = stack.peek();
        if (Objects.isNull(currentRoot)) {
            throw new AssertionError("Root node is null!");
        }
        return currentRoot.isSolution(vials);
    }

    private Function<Movement, Node> createChildNode(Node root) {
        return m -> new Node(m, root, root.childDepth());
    }

    private Consumer<Node> addToStackAndParent(Deque<Node> stack, Node parent) {
        return n -> {
            stack.push(n);
            parent.add(n);
        };
    }

    private static class Task extends RecursiveTask<Node> {
        private final Node root;
        private final Vials vials;
        private final AtomicBoolean solutionFound;

        public Task(Node root, Vials vials, AtomicBoolean solutionFound) {
            this.root = root;
            this.vials = vials;
            this.solutionFound = solutionFound;
        }

        @Override
        protected Node compute() {
            if (solutionFound.get()) {
                return null; // Stop processing if a solution is already found
            }

            if (Objects.isNull(root)) {
                throw new AssertionError("Root node is null!");
            }

            if (root.isSolution(vials)) {
                solutionFound.set(true);
                return root;
            }

            var tasks = vials.generatePossibleMoves()
                    .map(movement -> new Node(movement, root, root.childDepth()))
                    .map(childNode -> {
                        Vials copyVials = vials.copy();
                        copyVials.applyMovement(childNode.getMovement());
                        return new Task(childNode, copyVials, solutionFound);
                    })
                    .toList();

            Optional<Node> outcome = invokeAll(tasks.stream().takeWhile(t -> !solutionFound.get()).toList()).stream()
                    .map(Task::join)
                    .filter(Objects::nonNull)
                    .min(Comparator.comparingLong(Node::getDepth));

            if (outcome.isPresent()) {
                return outcome.get();
            }

            root.removeFromParent();
            vials.reverseMove();

            return null;
        }
    }
}
