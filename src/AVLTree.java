import java.util.Stack;

public class AVLTree<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;

    @Override
    public void insert(T data) {
        root = insert(root, data);
    }

    private Node<T> insert(Node<T> node, T data) {

        // 새로운 노드 생성
        if (node == null) {
            return new Node<>(data);
        }

        if (data.compareTo(node.getData()) < 0) {
            node.setLeftNode(insert(node.getLeftNode(), data));     // data가 더 작음 - 왼쪽 노드
        } else {
            node.setRightNode(insert(node.getRightNode(), data));   // data가 더 큼 - 오른쪽 노드
        }

        updateHeight(node);   // 높이 갱신
        return makeBalance(node);
    }

    @Override
    public void delete(T data) {
        root = delete(root, data);
    }

    private Node<T> delete (Node<T> node, T data) {
        if (node == null) {
            return node;
        }

        // 삭제할 데이터가 있는 노드 탐색
        if (data.compareTo(node.getData()) < 0) {   // data가 더 작음 - 왼쪽 노드
            node.setLeftNode(delete(node.getLeftNode(), data));
        } else if (data.compareTo(node.getData()) > 0) {    // data가 더 큼 - 오른쪽 노드
            node.setRightNode(delete(node.getRightNode(), data));
        } else {    // 삭제할 노드를 찾음
            // 1. leaf 노드인 경우
            // 그냥 삭제
            if (getHeight(node) == 0) return null;
            // 2. 하나의 자식 노드를 가지고 있는 경우
            // 자식 노드를 부모 노드 위치에 참조
            if (node.getRightNode() == null) {
                Node<T> n = node.getLeftNode();
                node = null;
                return n;
            }
            else if (node.getLeftNode() == null) {
                Node<T> n = node.getRightNode();
                node = null;
                return n;
            }
            // 3. 두개의 자식 노드를 가지고 있는 경우
            // 왼쪽 서브트리 중 가장 큰 수나 오른쪽 서브트리 중 가장 작은 수를 구하고, 삭제하려는 노드와 위치를 변경
            Node<T> n = getMaxDataNode(node.getLeftNode()); // 왼쪽 서브트리 중 가장 큰 data를 가진 노드
            node.setData(n.getData());
            node.setLeftNode(delete(node.getLeftNode(), node.getData()));  //
        }
        updateHeight(node);
        return makeBalance(node);
    }

    // node + 서브트리 중 가장 큰 data를 가진 노드를 반환
    private Node<T> getMaxDataNode(Node<T> node) {
        if (node.getRightNode() == null)
            return node;
        return getMaxDataNode(node.getRightNode());
    }

    // 노드가 불균형이면 회전연산 수행
    private Node<T> makeBalance(Node<T> node) {
        if (getBalance(node) < -1) {  // RR, RL
            if (getBalance(node.getRightNode()) > 0) { // RL
                node.setRightNode(rotateRight(node.getRightNode()));
            }
            return rotateLeft(node);
        } else if (getBalance(node) > 1) {  // LL, LR
            if (getBalance(node.getLeftNode()) < 0) {  // LR
                node.setLeftNode(rotateLeft(node.getLeftNode()));
            }
            return rotateRight(node);
        }
        return node;
    }

    // 반환값이 -1보다 작으면 RR, RL / 1보다 크면 LL, LR
    public int getBalance(Node<T> node) {
        return getHeight(node.getLeftNode()) - getHeight(node.getRightNode());
    }

    private Node<T> rotateRight(Node<T> node) {
        Node<T> newParentNode = node.getLeftNode();
        Node<T> newLeftNode = newParentNode.getRightNode();

        newParentNode.setRightNode(node);
        node.setLeftNode(newLeftNode);

        updateHeight(node);
        updateHeight(newParentNode);

        return newParentNode;
    }

    private Node<T> rotateLeft(Node<T> node) {
        Node<T> newParentNode = node.getRightNode();
        Node<T> newRightNode = newParentNode.getLeftNode();

        newParentNode.setLeftNode(node);
        node.setRightNode(newRightNode);

        updateHeight(node);
        updateHeight(newParentNode);

        return newParentNode;
    }

    private int getHeight(Node<T> node) {
        if (node == null) return -1;
        return node.getHeight();
    }

    private void updateHeight(Node<T> node) {
        node.setHeight(Math.max(getHeight(node.getLeftNode()), getHeight(node.getRightNode())) + 1);
    }

    @Override
    public void traverse() {
        if (root == null) {
            return;
        }
        System.out.print("inorder traversal : ");
        inOrderTraversal(root);
        System.out.println();
        System.out.print("preorder traversal : ");
        preOrderTraversal(root);
        System.out.println();
        System.out.print("postorder traversal : ");
        postOrderTraversal(root);
        System.out.println();

//        BTreePrinter.printNode(root);
    }

    //  중위 순회
    private void inOrderTraversal(Node<T> node) {
        if (node.getLeftNode() != null) {
            inOrderTraversal(node.getLeftNode());
        }
        System.out.print(node + ", ");
        if (node.getRightNode() != null) {
            inOrderTraversal(node.getRightNode());
        }
    }

    // 전위 순회
    private void preOrderTraversal(Node<T> node) {
        System.out.print(node + ", ");
        if (node.getLeftNode() != null) {
            preOrderTraversal(node.getLeftNode());
        }
        if (node.getRightNode() != null) {
            preOrderTraversal(node.getRightNode());
        }
    }

    // 후위 순회
    private void postOrderTraversal(Node<T> node) {
        if (node.getLeftNode() != null) {
            postOrderTraversal(node.getLeftNode());
        }
        if (node.getRightNode() != null) {
            postOrderTraversal(node.getRightNode());
        }
        System.out.print(node + ", ");
    }

}

