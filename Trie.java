package Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TrieNode 
{
	Map<Character, TrieNode> children;
	char c;
	boolean isLeaf;
	
	TrieNode(char c) 
	{
		this.c = c;
		children = new HashMap<>();
	}
}

public class Trie 
{
	
	TrieNode root;
	
	Trie(List<String> words)               //constructor
	{          
		root = new TrieNode('\0');
		for (String word : words)
			insert(word);
	}
	
	protected void insert(String word) 
	{
		if (word == null || word.isEmpty())
			return;
		
		TrieNode parent = root;
		for (char w : word.toCharArray()) 
		{
			TrieNode child = parent.children.get(w);
			
			if (child == null) 
			{
				child = new TrieNode(w);
				parent.children.put(w, child);
			}
			parent = child;
		}
        
		parent.isLeaf = true;
	}
	
	public boolean find(String prefix) 
	{
        TrieNode lastNode = root;
        for (char c : prefix.toCharArray()) 
        {
            lastNode = lastNode.children.get(c);
            if (lastNode == null)
                return false;
        }
        return lastNode.isLeaf;
    }
	
	protected boolean delete(String word) 
	{
		if (word == null || word.isEmpty())
			return false;
		
		if (!find(word))
			return false;
		
		TrieNode parent = root;
		TrieNode deleteBelow = null;
        char deleteChar = '\0';
        
		for (char w : word.toCharArray()) 
		{
			TrieNode child = parent.children.get(w);

			if (parent.children.size() > 1 || parent.isLeaf) 
			{ 
                deleteBelow = parent;
                deleteChar = w;
            }
			parent = child;
		}
        
		if (parent.children.isEmpty()) 
            deleteBelow.children.remove(deleteChar);
        else 
            parent.isLeaf = false;
		
        return true;
	}
	
	public List<String> search(String prefix) 
	{
        List<String> list = new ArrayList<>();
        TrieNode rootNode = root;
        StringBuffer current = new StringBuffer();
        for (char c : prefix.toCharArray()) 
        {
            rootNode = rootNode.children.get(c);
            if (rootNode == null)
                return list;
            current.append(c);
        }
        searcher(rootNode, list, current);
        return list;
    }
	
	public void searcher(TrieNode root, List<String> list, StringBuffer current) 
	{
        if (root.isLeaf) 
        {
            list.add(current.toString());
        }
 
        if (root.children == null || root.children.isEmpty())
            return;
 
        for (TrieNode child : root.children.values()) 
        {
            searcher(child, list, current.append(child.c));
            current.setLength(current.length() - 1);
        }
    }
}
