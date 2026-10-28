package datastruct;

import java.util.ArrayList;

public class BinarySearchTree {

    public static class Node {
        private String key;
        private ArrayList<String> values;
        private Node left;
        private Node right;


        public Node(String key, String value) {
            this.key = key;
            this.values = new ArrayList<String>();
            this.values.add(value);
        }
    }

    private Node root;
    private int size;
    public Node GetRoot() {return root;}

    public int GetSize(){return size;}


    //插入：如果关键字存在，追加数据
    public void insert(String key, String value){
        if(root == null){
            root = new Node(key, value);
            size = size ++;
            return;
        }
        Node p = root;
        while(true){
            int cmp = key.compareTo(p.key);
            if(cmp == 0){
                p.values.add(value);
                size = size++;
                return;
            } else if (cmp < 0) {
                if(p.left == null){
                    p.left = new Node(key, value);
                    size = size++;
                    return;
                }
                p = p.left;
            } else {
                if (p.right == null) {
                    p.right = new Node(key, value);
                    size = size + 1;
                    return;
                }
                p = p.right;
            }
        }
    }

    public ArrayList<String> search(String key){
        Node p = root;
        while(p != null){
            int cmp = key.compareTo(p.key);
            if(cmp == 0)
                return p.values;
            else if(cmp < 0)
                p = p.left;
            else
                p = p.right;

        }
        return new ArrayList<String>();
    }

    private Node deleteNode(Node p, String key){
        if(p == null){
            return null;
        }
        int cmp = key.compareTo(p.key);
        if(cmp < 0)
            p.left = deleteNode(p.left, key);
        else if(cmp > 0 )
            p.right = deleteNode(p.right, key);
        else{
            size = size - p.values.size();
            if(p.left == null){
                return p.right;
            }
            if(p.right == null){
                return p.left;
            }
            Node min = p.right;
            while(min.left != null){
                min = min.left;
            }
            String minKey = min.key;
            ArrayList<String> minValues = min.values;
            p.right = deleteNode(p.right, minKey);
            p.key = minKey;
            p.values = minValues;
        }
        return p;
    }

    public boolean delete(String key){
        int oldSize = size;
        root = deleteNode(root, key);
        return size < oldSize;
    }



    private int heightOf(Node p){
        if(p == null){
            return 0;
        }
        int leftHeight = heightOf(p.left);
        int rightHeight = heightOf(p.right);
        if(leftHeight > rightHeight){
            return leftHeight + 1;
        }
        return rightHeight + 1;
    }

    public int height(){return heightOf(root);}



    private int countOf(Node p){
        if(p == null)
            return 0;
        return countOf(p.left) + countOf(p.right) + 1;
    }

    public int countNodes(){return countOf(root);}

    private void inOrderVisit(Node p, ArrayList<String> result){
        if(p == null){
            return;
        }
        inOrderVisit(p.left, result);
        for(int i = 0; i < p.values.size(); i++){
            result.add(p.values.get(i));
        }
        inOrderVisit(p.right, result);
    }

    public ArrayList<String> inOrder(){
        ArrayList<String> result = new ArrayList<String>();
        inOrderVisit(root, result);
        return result;
    }



}
