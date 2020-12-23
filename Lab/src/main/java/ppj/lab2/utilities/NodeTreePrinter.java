package ppj.lab2.utilities;

import ppj.utilities.Node;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 30/11/2020
 */
public class NodeTreePrinter {
    public static String walkNodeTree(Node<?> rootNode) {
        return walkTreeHelper(rootNode, 0);
    }

    private static String walkTreeHelper(Node<?> node, int level) {
        StringBuilder sb = new StringBuilder();
        String spaces = " ".repeat(level);

        sb.append(spaces).append(node.getValue().toString()).append("\r\n");

        if (node.getChildren() != null)
            for (Node<?> child : node.getChildren()) {
                sb.append(walkTreeHelper(child, level + 1));
            }

        return sb.toString();
    }
}
