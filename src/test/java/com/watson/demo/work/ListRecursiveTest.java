package com.watson.demo.work;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : fengHangWen
 * @version : 1.0
 * @description : 树状List获取所有叶子节点路径测试代码
 * @company : 深圳一点盐光科技有限公司
 * @date : 2020/7/13 11:41
 */
public class ListRecursiveTest {

    public static void main(String[] args) {

        Node node1 = new Node("1", "1");
        Node node2 = new Node("2", "2");
        Node node3 = new Node("3", "3");
        Node node4 = new Node("4", "4");
        Node node5 = new Node("5", "5");
        Node node6 = new Node("6", "6");
        Node node7 = new Node("7", "7");

        node5.setChildern(new ArrayList<Node>() {{
            add(node6);
            add(node7);
        }});

        node3.setChildern(new ArrayList<Node>() {{
            add(node4);
            add(node5);
        }});

        node1.setChildern(new ArrayList<Node>() {{
            add(node2);
            add(node3);
        }});

        List<Node> path = new ArrayList<>();
        List<List<Node>> allPath = new ArrayList<>();
        path.add(node1);
        fun(node1, path, allPath);
        allPath.forEach(System.out::println);
    }

    private static void fun(Node node, List<Node> path, List<List<Node>> allPah) {

        if (CollectionUtils.isEmpty(node.getChildern())) {
            allPah.add(path);
            return;
        }
        for (Node cNode : node.getChildern()) {
            List<Node> cPath = new ArrayList<>(path);
            cPath.add(cNode);
            fun(cNode, cPath, allPah);
        }
    }

}

@Data
class Node {

    private String id;
    private String name;
    List<Node> childern;

    Node(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
