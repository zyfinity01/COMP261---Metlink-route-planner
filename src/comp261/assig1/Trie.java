package comp261.assig1;

import java.util.*;

public class Trie {
    TrieNode root = new TrieNode(); // the root node of the trie
    //List<Stop> stops = new ArrayList<Stop>();
    //Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();
	
    public Trie() {
	}

    public void add(char[] stopName, Stop stop){
        TrieNode node = root;
        for(Character c : stopName){
            TrieNode childNode = node.children.get(c);
            if(childNode == null){
                childNode = new TrieNode();
                node.children.put(c, childNode);
            }
            node = childNode;
        }
        node.data.add(stop);

    }

    public List<Stop> get(char[] stopName){
        TrieNode node = root;

        for(Character c : stopName){
            TrieNode childNode = node.children.get(c);
            if(childNode == null){
                return null;
            }
            node = childNode;
        }
        return node.data;
    }


    public List<Stop> getAll(char[] prefix){
        List<Stop> results = new ArrayList<Stop>();
        TrieNode node = root;

        for(Character c : prefix){
            TrieNode childNode = node.children.get(c);
            if(childNode == null){
                return null;
            }
            node = childNode;
        }
        getAllFrom(node, results);
        return results;
    }


    public void getAllFrom(TrieNode node, List<Stop> results){
        results.addAll(node.data);

        for(Character c : node.children.keySet()){
            getAllFrom(node.children.get(c), results);
        }
    }


    	/**
	 * Represents a single node in the trie. It contains a collection of the
	 * stops whose names are exactly the traversal down to this node.
	 */
	private class TrieNode {
		List<Stop> data = new ArrayList<>();
		Map<Character, TrieNode> children = new HashMap<>();
	}


    //ppt to pdf page 13 and up has good info    file:///C:/Users/Niraj/Downloads/lec03-graph2.pdf
    //decent javatpoint resource https://www.javatpoint.com/trie-data-structure



}

