package pl.me.jhonylemon;

import pl.me.jhonylemon.vial.Vials;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Depth First Search (DFS) algorithm for solving the Vials game.
 * This class extends the Solver class and implements the solve method.
 */
public class DepthFirstSearch extends Solver{

    private final ForkJoinPool pool = new ForkJoinPool();

    /**
     * Solves the Vials game using the Depth First Search algorithm. Finds the node that has all the vials filled with the same color.
     * {@inheritDoc}
     */
    @Override
    public Node solve(Vials vials) {
        long startTime = System.nanoTime();

        Node root = new Node(null, null);
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);

        while(!isSolution(stack, vials)) {
            Node currentRoot = stack.peek();

            if (Objects.isNull(currentRoot)) {
                throw new AssertionError("Root node is null!");
            }

            vials.generatePossibleMoves().map(createChildNode(currentRoot)).forEach(addToStackAndParent(stack, currentRoot));

            Node newRoot = stack.peek();
            if (Objects.nonNull(newRoot) && !currentRoot.isChildrenEmpty()) {
                vials.applyMovement(newRoot.getMovement());
            } else {
                stack.pop();
                currentRoot.removeFromParent();
                vials.reverseMove();
            }
        }

        long endTime = System.nanoTime();
        Duration duration = Duration.ofNanos(endTime - startTime);
        System.out.printf("Time taken: %sh %sm %ss %sms %sns %n",
                duration.toHoursPart(),
                duration.toMinutesPart(),
                duration.toSecondsPart(),
                duration.toMillisPart(),
                duration.toNanosPart()
        );

        return stack.peek();
    }


    private boolean isSolution(Deque<Node> stack, Vials vials) {
        Node currentRoot = stack.peek();
        if (Objects.isNull(currentRoot)) {
            throw new AssertionError("Root node is null!");
        }
        return currentRoot.isSolution(vials);
    }

    private Function<Movement, Node> createChildNode(Node root) {
        return m -> new Node(m, root);
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

        public Task(Node root, Vials vials) {
            this.root = root;
            this.vials = vials;
        }


        @Override
        protected Node compute()  {
            Deque<Node> stack = new ArrayDeque<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                Node currentRoot = stack.peek();

                if (Objects.isNull(currentRoot)) {
                    throw new AssertionError("Root node is null!");
                }

                if (currentRoot.isSolution(vials)) {
                    return currentRoot;
                }

                var tasks = vials.generatePossibleMoves()
                        .map(movement -> new Node(movement, currentRoot))
                        .map(childNode -> {
                            Vials copyVials = vials.copy();
                            copyVials.applyMovement(childNode.getMovement());
                            return new Task(childNode, copyVials);
                        })
                        .toList();

                invokeAll(tasks);

                for (Task task : tasks) {
                    Node result = task.join();
                    if (result != null) {
                        return result;
                    }
                }

                stack.pop();
                currentRoot.removeFromParent();
                vials.reverseMove();
            }

            return null;
        }
    }
}
