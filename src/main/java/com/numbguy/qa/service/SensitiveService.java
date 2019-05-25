package com.numbguy.qa.service;

import org.apache.commons.lang3.CharUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            InputStream word = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWord.txt");
            InputStreamReader reader= new InputStreamReader(word);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String lineText;
            while((lineText = bufferedReader.readLine()) != null) {
                addWord(lineText);
                System.out.println(lineText);
            }

            reader.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("读取敏感词失败");
        }
    }

    //增加关键词
    private void addWord(String lineText) {
        TrieNode tempNode = root;
        for(int i = 0; i < lineText.length();i++) {
            Character c = lineText.charAt(i);

            TrieNode node = tempNode.getNode(c);
            if(node == null) {
                node = new TrieNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;

            if(i == lineText.length() - 1) {
                tempNode.setWordEnd(true);
            }

        }
    }

    private  class TrieNode {
        //是不是关键词结尾
        private boolean isWord = false;

        //当前节点下的所有节点
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character key, TrieNode node) {
            subNodes.put(key, node);
        }

        public  TrieNode getNode(Character key) {
            return subNodes.get(key);
        }

        public  boolean isWordEnd(){ return isWord; }
        public  void setWordEnd(boolean wordEnd) {
            isWord = wordEnd;
        }
    }

    private  TrieNode root = new TrieNode();
    //
    private boolean isSymbol(char c) {
        int ic = (int)c;
        //东亚文字
        return !CharUtils.isAsciiAlphanumeric(c)&&(ic < 0x2E80||ic > 0x9FFF);
    }

    public  String filter(String text) {
        if(StringUtils.isEmpty(text)) {
            return  text;
        }

        StringBuilder result = new StringBuilder();
        String replacement = "**";
        TrieNode tempNode = root;
        int begin = 0;
        int position = 0;

        while(position < text.length()) {
            char c = text.charAt(position);
            if(isSymbol(c)) {
                if(tempNode == root) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }
            tempNode = tempNode.getNode(c);
            if(tempNode == null) {
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = root;
            }else if(tempNode.isWordEnd()) {
                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = root;
            }else {
                ++position;
            }
        }
        result.append(text.substring(begin));
        return  result.toString();
    }

    public static  void main(String[] argv) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("ab");
        s.addWord("de");
        System.out.println(s.filter("你 好 色 情"));
        System.out.println(s.filter("afcdef"));
    }
}
