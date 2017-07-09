package com.example.bill.delta.bean.topicnode.event;

import com.example.bill.delta.bean.topicnode.Node;
import java.util.List;

public class NodesEvent {
    private List<Node> nodeList;

    public NodesEvent(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }
}
