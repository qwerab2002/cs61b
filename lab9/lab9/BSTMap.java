package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        if (p.key.equals(key)) {
            return p.value;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySetHelper(root);
    }

    private Set<K> keySetHelper(Node p) {
        Set<K> keys = new HashSet<>();
        if (p != null) {
            keys.add(p.key);
            keys.addAll(keySetHelper(p.left));
            keys.addAll(keySetHelper(p.right));
        }
        return keys;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V val = this.get(key);
        if (val == null) {
            return null;
        }
        size--;
        root = removeHelper(key, root);
        return val;
    }
    private Node removeHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return removeHelper(key, p.left);
        } else if (cmp > 0) {
            return removeHelper(key, p.right);
        } else {
            if (p.left == null) {
                return p.right;
            }
            if (p.right == null) {
                return p.left;
            }
            Node t = p;
            p = min(p.right);
            p.right = delMin(t.right);
            p.left = t.left;

            /*
            p.key = min(p.right).key;
            p.value = min(p.right).value;
            p.right = delMin(p.right);
            */
        }
        return p;
    } //size is not updated here
    private void represent(Node p) {
        if (p == null) {
            System.out.println("null");
            return;
        } else {
            System.out.println(p.key);
        }
        if (!(p.left == null && p.right == null)) {
            System.out.println(p.key + " LEFT ");
            represent(p.left);
            System.out.println(p.key + " RIGHT ");
            represent(p.right);
        }
    }
    private Node min(Node p) {
        if (p == null) {
            return null;
        }
        if (p.left == null) {
            return p;
        }
        return min(p.left);
    }
    private Node delMin(Node p) {
        if (p == null) {
            return null;
        }
        if (p.left == null) {
            return p.right;
        }
        p.left = delMin(p.left);
        return p;
    } //size is not updated here
    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V val = this.get(key);
        if (val == null || val != value) {
            return null;
        }
        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();

    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        Integer rm = bstmap.remove("dog");
        Integer rm2 = bstmap.remove("hello");
        bstmap.represent(bstmap.root);
    }
}
