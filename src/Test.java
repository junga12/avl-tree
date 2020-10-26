public class Test {

    public static void main(String[] args) {
        Tree<String> tree = new AVLTree<>();
        tree.insert("A");
        tree.insert("B");
        tree.insert("C");
        tree.insert("D");
        tree.insert("E");
        tree.traverse();

        Tree<Integer> tree2 = new AVLTree<>();
        tree2.insert(30);
        tree2.insert(40);
        tree2.insert(100);
        tree2.insert(20);
        tree2.insert(10);
        tree2.insert(60);
        tree2.insert(70);
        tree2.insert(120);
        tree2.insert(110);
        tree2.traverse();
        tree2.delete(30);
        tree2.traverse();
        tree2.delete(110);
        tree2.traverse();
    }
}
